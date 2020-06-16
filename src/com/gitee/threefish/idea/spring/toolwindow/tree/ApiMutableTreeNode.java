package com.gitee.threefish.idea.spring.toolwindow.tree;


import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class ApiMutableTreeNode extends DefaultMutableTreeNode {

    public ApiMutableTreeNode() {
    }

    public ApiMutableTreeNode(TreeNodeObject nodeObjectNodeDescriptor) {
        this.userObject = nodeObjectNodeDescriptor;
    }


    @Override
    public Object getUserObject() {
        return this.userObject;
    }

    @Override
    public String toString() {
        return this.userObject.toString();
    }


}
