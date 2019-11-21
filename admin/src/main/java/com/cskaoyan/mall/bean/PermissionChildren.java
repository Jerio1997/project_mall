package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.Set;

@Data
public class PermissionChildren<T> {
    private String id;
    private String label;
    private Set<T> children;
}
