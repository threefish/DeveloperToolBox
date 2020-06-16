package com.gitee.threefish.idea.spring.toolwindow.action;

import com.gitee.threefish.idea.spring.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.gitee.threefish.idea.spring.toolwindow.tree.ApiMutableTreeNode;
import com.gitee.threefish.idea.spring.toolwindow.tree.TreeNodeObject;
import com.gitee.threefish.idea.spring.toolwindow.tree.TreeObjectTypeEnum;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtil;
import com.intellij.spring.web.mvc.mapping.UrlMapping;
import com.intellij.spring.web.mvc.model.mappings.UrlMappingPsiBasedElement;
import com.intellij.spring.web.mvc.services.SpringMvcService;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.xml.util.PsiElementPointer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.text.Collator;
import java.util.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class RefreshAction extends DumbAwareAction {


    private final static Comparator COMPARATOR = Collator.getInstance(Locale.ENGLISH);
    private final ToolWindowEx toolWindowEx;
    private final SimpleTree apiTree;

    public RefreshAction(String text, String description, Icon icon, ToolWindowEx toolWindowEx, SimpleTree apiTree) {
        super(text, description, icon);
        this.toolWindowEx = toolWindowEx;
        this.apiTree = apiTree;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.loadTree(e.getProject());
    }


    public void loadTree(Project project) {
        Module[] modules = ModuleManager.getInstance(toolWindowEx.getProject()).getModules();
        DumbService.getInstance(toolWindowEx.getProject()).smartInvokeLater(() -> {
            Task.Backgroundable backgroundable = new Task.Backgroundable(project, "Spring Api 扫描中...") {
                @Override
                public void run(@NotNull ProgressIndicator progressIndicator) {
                    ApplicationManager.getApplication().runReadAction(() -> {
                        Set<String> repeat = new HashSet<>();
                        //定义tree 的根目录
                        int size = 0;
                        ApiMutableTreeNode root = new ApiMutableTreeNode();
                        for (Module module : modules) {
                            SpringMvcService springMvcService = SpringMvcService.getInstance();
                            Set<UrlMapping<?>> urlMappings = springMvcService.getUrlMappings(module);
                            ApiMutableTreeNode apiMutableTreeNode = new ApiMutableTreeNode(new TreeNodeObject(project, TreeObjectTypeEnum.MODULE, module.getName()));
                            List<ApiMutableTreeNode> list = new ArrayList<>();
                            for (UrlMapping<?> urlMapping : urlMappings) {
                                if (urlMapping instanceof UrlMappingPsiBasedElement) {
                                    UrlMappingPsiBasedElement urlMappingPsiBasedElement = (UrlMappingPsiBasedElement) urlMapping;
                                    PsiElementPointer definition = urlMappingPsiBasedElement.getDefinition();
                                    String moduleDirPath = ModuleUtil.getModuleDirPath(module);
                                    String canonicalPath = PsiUtil.getVirtualFile(definition.getPsiElement()).getCanonicalPath();
                                    if (canonicalPath.startsWith(moduleDirPath)) {
                                        //是这个模块的api
                                        size = size + 1;
                                        progressIndicator.setText(urlMapping.getURL());
                                        PsiElement psiElement = urlMappingPsiBasedElement.getDefinition().getPsiElement();
                                        repeat.add(urlMapping.getURL());
                                        list.add(new ApiMutableTreeNode(
                                                new TreeNodeObject(project,
                                                        new SpringRequestMappingNavigationItem(psiElement, urlMapping.getURL()),
                                                        urlMappingPsiBasedElement.getMethod()))
                                        );
                                    }
                                }
                            }
                            if (list.size() > 0) {
                                Collections.sort(list, (o1, o2) -> COMPARATOR.compare(o1.toString(), o2.toString()));
                                list.forEach(mutableTreeNode -> apiMutableTreeNode.add(mutableTreeNode));
                            }
                            if (apiMutableTreeNode.getChildCount() > 0) {
                                root.add(apiMutableTreeNode);
                            }
                        }
                        root.setUserObject(new TreeNodeObject(project, TreeObjectTypeEnum.ROOT, "Found " + size + " api"));
                        apiTree.setModel(new DefaultTreeModel(root));
                    });
                }
            };
            BackgroundableProcessIndicator backgroundableProcessIndicator = new BackgroundableProcessIndicator(backgroundable);
            backgroundableProcessIndicator.setIndeterminate(true);
            ProgressManager.getInstance().runProcessWithProgressAsynchronously(backgroundable, backgroundableProcessIndicator);
        });

    }

}
