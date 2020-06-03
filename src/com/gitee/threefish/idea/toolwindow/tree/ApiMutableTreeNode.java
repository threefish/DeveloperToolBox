package com.gitee.threefish.idea.toolwindow.tree;


import com.gitee.threefish.idea.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.intellij.spring.web.mvc.jam.RequestMethod;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class ApiMutableTreeNode extends DefaultMutableTreeNode {

    private SpringRequestMappingNavigationItem urlMappingPsiBasedElement;

    private final TreeObjectType treeObjectType;

    private String name;

    private RequestMethod[] requestMethods;

    public ApiMutableTreeNode(TreeObjectType treeObjectType, String name) {
        super(name);
        this.treeObjectType = treeObjectType;
        this.name = name;
    }

    public ApiMutableTreeNode(SpringRequestMappingNavigationItem urlMappingPsiBasedElement, RequestMethod[] requestMethods) {
        super(urlMappingPsiBasedElement.getText());
        this.name = urlMappingPsiBasedElement.getText();
        this.urlMappingPsiBasedElement = urlMappingPsiBasedElement;
        this.treeObjectType = TreeObjectType.REQUEST;
        this.requestMethods = requestMethods;

    }

    @Override
    public String toString() {
        return name;
    }

    public SpringRequestMappingNavigationItem getSpringRequestMappingNavigationItem() {
        return urlMappingPsiBasedElement;
    }

    public TreeObjectType getTreeObjectType() {
        return treeObjectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }
}
