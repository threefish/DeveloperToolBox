package com.gitee.threefish.idea.toolwindow.ui;

import com.gitee.threefish.idea.toolwindow.tree.ApiMutableTreeNode;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/4
 */
public class RestServicesNavigatorPanel extends SimpleToolWindowPanel implements DataProvider {

    private final Project myProject;

    private final SimpleTree myTree;
    private Splitter servicesContentPaneSplitter;
    final ActionManager actionManager = ActionManager.getInstance();


    public RestServicesNavigatorPanel(Project project, SimpleTree tree) {
        super(true, true);
        myProject = project;
        myTree = tree;
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
        servicesContentPaneSplitter = new Splitter(true, .5f);
        servicesContentPaneSplitter.setShowDividerControls(true);
        servicesContentPaneSplitter.setDividerWidth(10);
        servicesContentPaneSplitter.setBorder(BorderFactory.createLineBorder(Color.RED));
        servicesContentPaneSplitter.setFirstComponent(scrollPane);
        setContent(servicesContentPaneSplitter);
    }



}
