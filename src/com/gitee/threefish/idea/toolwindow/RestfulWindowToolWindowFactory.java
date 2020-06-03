package com.gitee.threefish.idea.toolwindow;

import com.gitee.threefish.idea.toolwindow.action.ApiTreeMouseAdapter;
import com.gitee.threefish.idea.toolwindow.action.RefreshAction;
import com.gitee.threefish.idea.toolwindow.ui.RestfulTreePanel;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import icons.SpringApiIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * ActionManager actionManager = ActionManager.getInstance();
 * ((ToolWindowEx) toolWindow).setTitleActions(actionManager.getAction("MyAction"));
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TITLE = "Spring Api Tool";
    private final RestfulTreePanel restfulTreePanel = new RestfulTreePanel();
    private ToolWindowEx toolWindowEx;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JTree apiTree = restfulTreePanel.getApiTree();
        apiTree.setModel(null);
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        RefreshAction refreshAction = new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh, toolWindowEx, restfulTreePanel.getApiTree());
        toolWindowEx.setTitleActions(refreshAction);
        apiTree.addMouseListener(new ApiTreeMouseAdapter(apiTree));
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(restfulTreePanel.getRootPanel(), null, false);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
        if (project.isInitialized()) {
            refreshAction.loadTree(project);
        }
    }


    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        toolWindowEx.setStripeTitle(TITLE);
        toolWindowEx.setIcon(SpringApiIcons.Spring);
        toolWindowEx.setTitle(TITLE);
    }

}