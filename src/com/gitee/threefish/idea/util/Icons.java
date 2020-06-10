package com.gitee.threefish.idea.util;

import com.intellij.openapi.util.IconLoader;
import com.intellij.spring.web.mvc.jam.RequestMethod;
import icons.SpringApiIcons;

import javax.swing.*;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/10
 */
public class Icons {
    public static final Icon POST = IconLoader.findIcon("/icons/POST.png");
    public static final Icon GET = IconLoader.findIcon("/icons/GET.png");
    public static final Icon PUT = IconLoader.findIcon("/icons/PUT.png");
    public static final Icon DELETE = IconLoader.findIcon("/icons/DELETE.png");


    public static Icon getMethodIcon(RequestMethod[] method) {
        Icon icon = SpringApiIcons.RequestMapping;
        if (Objects.isNull(method) || method.length == 0) {
            return icon;
        }
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
        return icon;
    }
}
