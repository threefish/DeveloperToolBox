package com.gitee.threefish.idea.toolwindow;

import com.gitee.threefish.idea.toolwindow.action.ApiTreeMouseAdapter;
import com.gitee.threefish.idea.toolwindow.action.RefreshAction;
import com.gitee.threefish.idea.toolwindow.ui.RestServicesNavigatorPanel;
import com.gitee.threefish.idea.toolwindow.ui.RestfulTreePanel;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.SimpleTree;
import icons.SpringApiIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

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
    private SimpleTree myTree;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JTree apiTree = restfulTreePanel.getApiTree();
        apiTree.setModel(null);
        initTree(project);
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        RefreshAction refreshAction = new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh, toolWindowEx, myTree);
        toolWindowEx.setTitleActions(refreshAction);
        myTree.addMouseListener(new ApiTreeMouseAdapter(apiTree));
        JPanel panel = new RestServicesNavigatorPanel(project, myTree);
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(panel, null, false);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
        if (project.isInitialized()) {
            refreshAction.loadTree(project);
        }
    }


    private void initTree(Project project) {
        myTree = new SimpleTree() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final JLabel myLabel = new JLabel("xxx");
                if (project.isInitialized()) {
                    return;
                }
                myLabel.setFont(getFont());
                myLabel.setBackground(getBackground());
                myLabel.setForeground(getForeground());
                Rectangle bounds = getBounds();
                Dimension size = myLabel.getPreferredSize();
                myLabel.setBounds(0, 0, size.width, size.height);

                int x = (bounds.width - size.width) / 2;
                Graphics g2 = g.create(bounds.x + x, bounds.y + 20, bounds.width, bounds.height);
                try {
                    myLabel.paint(g2);
                } finally {
                    g2.dispose();
                }
            }
        };
        myTree.getEmptyText().clear();
        myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        toolWindowEx.setStripeTitle(TITLE);
        toolWindowEx.setIcon(SpringApiIcons.Spring);
        toolWindowEx.setTitle(TITLE);
    }

}
