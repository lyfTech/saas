package org.saas.common.handle;

import java.util.List;

/**
 * Created by gls on 2016/12/22.
 */
public class Tree {
    private Long id;
    private String state;
    private TreeNode attributes;
    private List<Tree> children;

    public Tree(Long id, String state, TreeNode attributes, List<Tree> children) {
        this.id = id;
        this.state = state;
        this.attributes = attributes;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TreeNode getAttributes() {
        return attributes;
    }

    public void setAttributes(TreeNode attributes) {
        this.attributes = attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
