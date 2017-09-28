/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.examples.client;

import com.coderli.log.MyLogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author li.hzh
 */
public class PoolBlockTest {

    public static void main(String[] args) throws Exception {
        //默认情况，不消费，不释放，３次调用直接block。
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://httpbinss.org/get");
            CloseableHttpResponse response1 = null;
            try {
                response1 = httpclient.execute(httpGet);
                MyLogFactory.getLog().debug("Get Response Line: " + response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
//                httpclient.close();
                CloseableHttpResponse response2 = httpclient.execute(httpGet);
                CloseableHttpResponse response3 = httpclient.execute(httpGet);
                MyLogFactory.getLog().info("3 times request finished.");
            } finally {
                if (response1 != null) {
                    response1.close();
                }
            }
        } finally {
            httpclient.close();
        }
    }

}
