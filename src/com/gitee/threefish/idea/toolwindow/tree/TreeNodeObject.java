package com.gitee.threefish.idea.toolwindow.tree;

import com.gitee.threefish.idea.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.spring.web.mvc.jam.RequestMethod;
import icons.SpringApiIcons;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/5
 */
public class TreeNodeObject extends NodeDescriptor {


    private static final Icon POST = IconLoader.findIcon("/icons/POST.png");
    private static final Icon GET = IconLoader.findIcon("/icons/GET.png");
    private static final Icon PUT = IconLoader.findIcon("/icons/PUT.png");
    private static final Icon DELETE = IconLoader.findIcon("/icons/DELETE.png");
    private final TreeObjectType treeObjectType;
    private SpringRequestMappingNavigationItem urlMappingPsiBasedElement;
    private final String name;
    private final Project project;
    private RequestMethod[] requestMethods;

    public TreeNodeObject(Project project, TreeObjectType treeObjectType, String name) {
        super(project, null);
        this.project = project;
        this.treeObjectType = treeObjectType;
        this.name = name;
        this.init();
    }

    public TreeNodeObject(Project project, SpringRequestMappingNavigationItem urlMappingPsiBasedElement, RequestMethod[] requestMethods) {
        super(project, null);
        this.project = urlMappingPsiBasedElement.getProject();
        this.name = urlMappingPsiBasedElement.getText();
        this.urlMappingPsiBasedElement = urlMappingPsiBasedElement;
        this.treeObjectType = TreeObjectType.REQUEST;
        this.requestMethods = requestMethods;
        this.init();
    }

    private void init() {
        Icon icon = SpringApiIcons.SpringWeb;
        //根据数据节点里的nodeType数据决定节点图标
        if (this.treeObjectType == TreeObjectType.REQUEST) {
            if (requestMethods.length > 0) {
                RequestMethod requestMethod = requestMethods[0];
                switch (requestMethod) {
                    case GET:
                        icon = GET;
                        break;
                    case HEAD:
                        break;
                    case POST:
                        icon = POST;
                        break;
                    case PUT:
                        icon = PUT;
                        break;
                    case PATCH:
                        break;
                    case DELETE:
                        icon = DELETE;
                        break;
                    case OPTIONS:
                        break;
                    case TRACE:
                        break;
                    default:
                        break;
                }

            }
        } else if (this.treeObjectType == TreeObjectType.MODULE) {
            icon = AllIcons.Nodes.ModuleGroup;
        } else if (this.treeObjectType == TreeObjectType.ROOT) {
            icon = SpringApiIcons.Spring;
        }
        this.setIcon(icon);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Object getElement() {
        return null;
    }


    public TreeObjectType getTreeObjectType() {
        return treeObjectType;
    }

    public SpringRequestMappingNavigationItem getUrlMappingPsiBasedElement() {
        return urlMappingPsiBasedElement;
    }
}
