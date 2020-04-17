/**
 * 
 * <p>更改FreeSWITCH的ESL的java源码</p>
 *
 */

package org.freeswitch.esl.client;



/**

支持订阅json类型的事件：
	如：applicationContextFsStatus.xml

	更改：
		EslHeaders	123行	 添加JSON类型
		EslEvent	77行		添加按JSON解析
					229行	按JSON解析
		AbstractEslClientHandler	77行		添加JSON支持

*/

