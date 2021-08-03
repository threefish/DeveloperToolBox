package com.gitee.threefish.idea.util;

import com.intellij.spring.SpringApiIcons;
import com.intellij.spring.mvc.jam.RequestMethod;


import javax.swing.*;
import java.util.Objects;

import static com.intellij.openapi.util.IconLoader.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/6/10
 */
public class Icons {
    public static final Icon POST = findIcon("/icons/POST.png");
    public static final Icon GET = findIcon("/icons/GET.png");
    public static final Icon PUT = findIcon("/icons/PUT.png");
    public static final Icon DELETE = findIcon("/icons/DELETE.png");


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
