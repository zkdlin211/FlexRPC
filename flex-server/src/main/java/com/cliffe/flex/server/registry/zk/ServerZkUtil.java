package com.cliffe.flex.server.registry.zk;

import com.cliffe.flex.server.FlexServerZkRegistrationConfigurationProperties;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Zookeeper连接操作接口
 */
@Component
public class ServerZkUtil {

    @Resource
    private ZkClient zkClient;

    @Autowired
    private FlexServerZkRegistrationConfigurationProperties zkServerConfiguration;

    /***
     * create root node
     */
    public void createRootNode() {
        boolean exists = zkClient.exists(zkServerConfiguration.getRoot());
        if (!exists) {
            zkClient.createPersistent(zkServerConfiguration.getRoot());
        }
    }

    /***
     * create persistent node
     * @param path
     */
    public void createPersistentNode(String path) {
        String pathName = zkServerConfiguration.getRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createPersistent(pathName);
        }
    }

    /***
     * create ephemeral node
     * @param path
     */
    public void createNode(String path) {
        String pathName = zkServerConfiguration.getRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createEphemeral(pathName);
        }
    }
}