package com.gitee.threefish.idea.spring.toolwindow.tree;

import com.gitee.threefish.idea.spring.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.gitee.threefish.idea.util.Icons;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.spring.web.mvc.jam.RequestMethod;
import icons.SpringApiIcons;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/5
 */
public class TreeNodeObject extends NodeDescriptor {


    private final TreeObjectTypeEnum treeObjectType;
    private final String name;
    private final Project project;
    private SpringRequestMappingNavigationItem urlMappingPsiBasedElement;
    private RequestMethod[] requestMethods;

    public TreeNodeObject(Project project, TreeObjectTypeEnum treeObjectType, String name) {
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
        this.treeObjectType = TreeObjectTypeEnum.REQUEST;
        this.requestMethods = requestMethods;
        this.init();
    }

    private void init() {
        Icon icon = SpringApiIcons.SpringWeb;
        //根据数据节点里的nodeType数据决定节点图标
        if (this.treeObjectType == TreeObjectTypeEnum.REQUEST) {
            icon = Icons.getMethodIcon(requestMethods);
        } else if (this.treeObjectType == TreeObjectTypeEnum.MODULE) {
            icon = AllIcons.Nodes.ModuleGroup;
        } else if (this.treeObjectType == TreeObjectTypeEnum.ROOT) {
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


    public TreeObjectTypeEnum getTreeObjectType() {
        return treeObjectType;
    }

    public SpringRequestMappingNavigationItem getUrlMappingPsiBasedElement() {
        return urlMappingPsiBasedElement;
    }
}
