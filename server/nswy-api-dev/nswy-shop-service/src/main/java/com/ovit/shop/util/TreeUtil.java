package com.ovit.shop.util;

import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树 结构
 */
public class TreeUtil {


    List<Map<String,Object>> nodeList = new ArrayList<>();

    List<Map<String,Object>> resultList = new ArrayList<>();

    /**
     * 树行结构
     * @param params
     * @return
     */
    public List<Map<String,Object>> treeNodeList(List<Map<String,Object>> params){

        nodeList = params;
        List<Map<String,Object>> treeNodes = new ArrayList<>();
        List<Map<String,Object>> rootNodes = this.getRootNodes(nodeList);
        for (Map<String,Object> rootNode : rootNodes) {

            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    /**
     * 递归出同级数组结构
     * @param params
     * @return
     */
    public List<Map<String,Object>> recursionNodeList(List<Map<String,Object>> params){

        nodeList = params;
        List<Map<String,Object>> recursionNodeList = new ArrayList<>();
        List<Map<String,Object>> rootNodes = this.getRootNodes(nodeList);
        for (Map<String,Object> rootNode : rootNodes) {
            resultList.add(rootNode);
            recursionNodes(rootNode);


        }
        recursionNodeList = resultList;
        return recursionNodeList;
    }

    // 获取集合中所有的根节点
    public List<Map<String,Object>> getRootNodes(List<Map<String,Object>> list) {
        List<Map<String,Object>> rootNodes = new ArrayList<>();
        for (Map<String,Object> node : list) {
            if (rootNode(node)) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    // 判断一级节点
    public boolean rootNode(Map<String,Object> nodes) {
        boolean isRootNode = false;
        if("0".equals(MapUtils.getString(nodes,"pid"))){
            isRootNode = true;

        }
        return isRootNode;
    }

    // 递归子节点生成树行结构
    public void buildChildNodes(Map<String,Object> node) {
        List<Map<String,Object>> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Map<String,Object> child : children) {

                buildChildNodes(child);

            }
            node.put("children",children);

        }else{
            node.put("children",new String[]{});
        }
    }

    // 获取父节点下所有的子节点
    public List<Map<String,Object>> getChildNodes(Map<String,Object> pnode) {
        List<Map<String,Object>> childNodes = new ArrayList<>();
        for (Map<String,Object> n : nodeList) {
            if(MapUtils.getString(pnode,"id").equals(MapUtils.getString(n,"pid"))){
                n.put("checked",true);
                childNodes.add(n);
            }
        }
        return childNodes;
    }


    // 递归子节点
    public void recursionNodes(Map<String,Object> node) {
        List<Map<String,Object>> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Map<String,Object> child : children) {
                resultList.add(child);
                recursionNodes(child);

            }

        }
    }
}
