package org.opensjp.openbigpipe.annotation;

/**
 * work mode.
 * CONCURRNET: concurrent render pagelet in server,
 *          after all pagelet renderd and merge them as one html reponse to browse
 * BIGPIPE: concurrent render pagelet in server, different from concurrent mode ,
 *          bigpipe mode will flush pagelet result to brower as soon as one pagelet renderd
 */
public enum  ExecuteType {
    /** 后台获取数据采用并行方式，按pagelet的有优先级并行获取数据和渲染页面．最后一起发送给前端  **/
    CONCURRNET,
    /** 采用bigpipe的方式获取和传送数据．并行获取数据和渲染页面．**/
    BIGPIPE;
}
