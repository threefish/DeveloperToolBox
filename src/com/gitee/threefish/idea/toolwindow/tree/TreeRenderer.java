package com.gitee.threefish.idea.toolwindow.tree;


import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.spring.web.mvc.jam.RequestMethod;
import icons.SpringApiIcons;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/3
 */
public class TreeRenderer extends DefaultTreeCellRenderer {


    private static final Icon POST = IconLoader.findIcon("/icons/POST.png");
    private static final Icon GET = IconLoader.findIcon("/icons/GET.png");
    private static final Icon PUT = IconLoader.findIcon("/icons/PUT.png");
    private static final Icon DELETE = IconLoader.findIcon("/icons/DELETE.png");


    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        //执行父类默认的节点绘制操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        ApiMutableTreeNode node = (ApiMutableTreeNode) value;
        Icon icon = SpringApiIcons.SpringWeb;
        //根据数据节点里的nodeType数据决定节点图标
        if (node.getTreeObjectType() == TreeObjectType.REQUEST) {
            RequestMethod[] method = node.getRequestMethods();
            if (method.length > 0) {
                RequestMethod requestMethod = method[0];
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
        } else if (node.getTreeObjectType() == TreeObjectType.MODULE) {
            icon = AllIcons.Nodes.ModuleGroup;
        } else if (node.getTreeObjectType() == TreeObjectType.ROOT) {
            this.setIcon(SpringApiIcons.Spring);
            icon = SpringApiIcons.Spring;
        }
        this.setIcon(icon);
        return this;
    }
}
