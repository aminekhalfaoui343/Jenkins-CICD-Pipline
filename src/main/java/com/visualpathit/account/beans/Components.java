package com.visualpathit.account.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Holds external service configuration injected from application properties.
 */
@Component
public final class Components {
    /** Active Memcached server host. */
    @Value("${memcached.active.host}")
    private String activeHost;
    /** Active Memcached server port. */
    @Value("${memcached.active.port}")
    private String activePort;
    /** Standby Memcached server host. */
    @Value("${memcached.standBy.host}")
    private String standByHost;
    /** Standby Memcached server port. */
    @Value("${memcached.standBy.port}")
    private String standByPort;
    /** RabbitMQ broker host. */
    @Value("${rabbitmq.address}")
    private String rabbitMqHost;
    /** RabbitMQ broker port. */
    @Value("${rabbitmq.port}")
    private String rabbitMqPort;
    /** RabbitMQ username. */
    @Value("${rabbitmq.username}")
    private String rabbitMqUser;
    /** RabbitMQ password. */
    @Value("${rabbitmq.password}")
    private String rabbitMqPassword;
    /** Elasticsearch host. */
    @Value("${elasticsearch.host}")
    private String elasticsearchHost;
    /** Elasticsearch port. */
    @Value("${elasticsearch.port}")
    private String elasticsearchPort;
    /** Elasticsearch cluster name. */
    @Value("${elasticsearch.cluster}")
    private String elasticsearchCluster;
    /** Elasticsearch node name. */
    @Value("${elasticsearch.node}")
    private String elasticsearchNode;

    /** @return active Memcached host */
    public String getActiveHost() {
        return activeHost;
    }

    /**
     * Sets the active Memcached host.
     *
     * @param newActiveHost configured host
     */
    public void setActiveHost(final String newActiveHost) {
        this.activeHost = newActiveHost;
    }

    /** @return active Memcached port */
    public String getActivePort() {
        return activePort;
    }

    /**
     * Sets the active Memcached port.
     *
     * @param newActivePort configured port
     */
    public void setActivePort(final String newActivePort) {
        this.activePort = newActivePort;
    }

    /** @return standby Memcached host */
    public String getStandByHost() {
        return standByHost;
    }

    /**
     * Sets the standby Memcached host.
     *
     * @param newStandByHost configured host
     */
    public void setStandByHost(final String newStandByHost) {
        this.standByHost = newStandByHost;
    }

    /** @return standby Memcached port */
    public String getStandByPort() {
        return standByPort;
    }

    /**
     * Sets the standby Memcached port.
     *
     * @param newStandByPort configured port
     */
    public void setStandByPort(final String newStandByPort) {
        this.standByPort = newStandByPort;
    }

    /** @return RabbitMQ host */
    public String getRabbitMqHost() {
        return rabbitMqHost;
    }

    /**
     * Sets the RabbitMQ host.
     *
     * @param newRabbitMqHost configured host
     */
    public void setRabbitMqHost(final String newRabbitMqHost) {
        this.rabbitMqHost = newRabbitMqHost;
    }

    /** @return RabbitMQ port */
    public String getRabbitMqPort() {
        return rabbitMqPort;
    }

    /**
     * Sets the RabbitMQ port.
     *
     * @param newRabbitMqPort configured port
     */
    public void setRabbitMqPort(final String newRabbitMqPort) {
        this.rabbitMqPort = newRabbitMqPort;
    }

    /** @return RabbitMQ username */
    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    /**
     * Sets the RabbitMQ username.
     *
     * @param newRabbitMqUser configured username
     */
    public void setRabbitMqUser(final String newRabbitMqUser) {
        this.rabbitMqUser = newRabbitMqUser;
    }

    /** @return RabbitMQ password */
    public String getRabbitMqPassword() {
        return rabbitMqPassword;
    }

    /**
     * Sets the RabbitMQ password.
     *
     * @param newRabbitMqPassword configured password
     */
    public void setRabbitMqPassword(final String newRabbitMqPassword) {
        this.rabbitMqPassword = newRabbitMqPassword;
    }

    /** @return Elasticsearch host */
    public String getElasticsearchHost() {
        return elasticsearchHost;
    }

    /**
     * Sets the Elasticsearch host.
     *
     * @param newElasticsearchHost configured host
     */
    public void setElasticsearchHost(final String newElasticsearchHost) {
        this.elasticsearchHost = newElasticsearchHost;
    }

    /** @return Elasticsearch port */
    public String getElasticsearchPort() {
        return elasticsearchPort;
    }

    /**
     * Sets the Elasticsearch port.
     *
     * @param newElasticsearchPort configured port
     */
    public void setElasticsearchPort(final String newElasticsearchPort) {
        this.elasticsearchPort = newElasticsearchPort;
    }

    /** @return Elasticsearch cluster name */
    public String getElasticsearchCluster() {
        return elasticsearchCluster;
    }

    /**
     * Sets the Elasticsearch cluster name.
     *
     * @param newElasticsearchCluster configured cluster name
     */
    public void setElasticsearchCluster(
            final String newElasticsearchCluster) {
        this.elasticsearchCluster = newElasticsearchCluster;
    }

    /** @return Elasticsearch node name */
    public String getElasticsearchNode() {
        return elasticsearchNode;
    }

    /**
     * Sets the Elasticsearch node name.
     *
     * @param newElasticsearchNode configured node name
     */
    public void setElasticsearchNode(final String newElasticsearchNode) {
        this.elasticsearchNode = newElasticsearchNode;
    }
}
