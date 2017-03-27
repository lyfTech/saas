package org.saas.service.system.helper;

import org.saas.dao.domain.SysPerm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gls on 2016/12/15.
 */
public class TreeUtil {
    private List<SysPerm> treeNodeList = new ArrayList<SysPerm>();

    public TreeUtil(List<SysPerm> list) {
        treeNodeList = list;
    }

    /**
     * @param nodeId
     * @return
     */
    public SysPerm getNodeById(Long nodeId) {
        SysPerm treeNode = new SysPerm();
        for (SysPerm item : treeNodeList) {
            if (item.getId().equals(nodeId)) {
                treeNode = item;
                break;
            }
        }
        return treeNode;
    }

    /**
     * @param nodeId
     * @return
     */
    public List<SysPerm> getChildrenNodeById(Long nodeId) {
        List<SysPerm> childrenTreeNode = new ArrayList<SysPerm>();
        for (SysPerm item : treeNodeList) {
            if (item.getParentId().equals(nodeId)) {
                childrenTreeNode.add(item);
            }
        }
        return childrenTreeNode;
    }

    /**
     * 递归生成Tree结构数据
     *
     * @param pId
     * @return
     */
    public List<SysPerm> generateTreeNode(SysPerm p, Long pId) {
        if (p == null) return null;
        List<SysPerm> perms = new ArrayList<SysPerm>();
        for (Iterator<SysPerm> iterator = treeNodeList.iterator(); iterator.hasNext(); ) {
            SysPerm perm = iterator.next();
            if (perm.getParentId() != null && perm.getParentId().equals(pId)) {
                perms.add(perm);
                generateTreeNode(perm, perm.getId());
            }
        }
        p.setChilds(perms);
        return perms;
    }
}
