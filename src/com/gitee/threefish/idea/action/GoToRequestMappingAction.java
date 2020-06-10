package com.gitee.threefish.idea.action;

import com.gitee.threefish.idea.gotosymbol.AtMappingModel;
import com.gitee.threefish.idea.toolwindow.navigation.SpringRequestMappingNavigationItem;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/10
 */
public class GoToRequestMappingAction extends GotoActionBase {
    @Override
    protected void gotoActionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        this.showNavigationPopup(anActionEvent, new AtMappingModel(project), new GoToRequestMappingActionCallback(), true);
    }

    private class GoToRequestMappingActionCallback extends GotoActionBase.GotoActionCallback<String> {
        @Override
        public void elementChosen(ChooseByNamePopup popup, Object element) {
            if (element instanceof SpringRequestMappingNavigationItem) {
                SpringRequestMappingNavigationItem el = (SpringRequestMappingNavigationItem) element;
                if (el.canNavigate()) {
                    el.navigate(true);
                }
            }
        }
    }
}
