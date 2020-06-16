package com.gitee.threefish.idea.spring.toolwindow.action;


import com.gitee.threefish.idea.spring.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.gitee.threefish.idea.spring.toolwindow.tree.ApiMutableTreeNode;
import com.gitee.threefish.idea.spring.toolwindow.tree.TreeNodeObject;
import com.gitee.threefish.idea.spring.toolwindow.tree.TreeObjectTypeEnum;
import com.intellij.ui.treeStructure.SimpleTree;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class ApiTreeMouseAdapter extends MouseAdapter {

    private final SimpleTree apiTree;

    public ApiTreeMouseAdapter(SimpleTree apiTree) {
        this.apiTree = apiTree;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 如果在这棵树上点击了2次,即双击
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            // 按照鼠标点击的坐标点获取路径
            TreePath selPath = apiTree.getPathForLocation(e.getX(), e.getY());
            // 谨防空指针异常!双击空白处是会这样
            if (Objects.nonNull(selPath)) {
                // 获取这个路径上的最后一个组件,也就是双击的地方
                ApiMutableTreeNode node = (ApiMutableTreeNode) selPath.getLastPathComponent();
                if (Objects.nonNull(node)) {
                    TreeNodeObject userObject = (TreeNodeObject) node.getUserObject();
                    if (!(userObject.getTreeObjectType() == TreeObjectTypeEnum.MODULE || userObject.getTreeObjectType() == TreeObjectTypeEnum.ROOT)) {
                        SpringRequestMappingNavigationItem springRequestMappingNavigationItem = userObject.getUrlMappingPsiBasedElement();
                        //PsiNavigateUtil.navigate(springRequestMappingNavigationItem);
                        if (Objects.nonNull(node) && springRequestMappingNavigationItem.canNavigate()) {
                            springRequestMappingNavigationItem.navigate(true);
                        }
                    }
                }
            }
        }
    }
}
