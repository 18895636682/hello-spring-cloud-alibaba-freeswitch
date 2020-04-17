package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Data
public class ChannelCdrView implements Serializable {

    private static final long serialVersionUID = -2730577264111399823L;

    private String id;                            // 记录ID
    private Integer directionType;                // 呼叫方向类型{1 : 呼出, 2 : 呼入, 3 : 内部呼叫}
    private Integer dialType;                    // 呼叫类型{1 : 普通呼叫, 2 : 自动群呼, 3 : 语音群发  ,4 : 群呼}
    private Boolean isCaller;                    // 是否为主叫

    /*************** 下面是记录在通道变量中，或事件消息头中的属性 *******************/
    private String variable_zhuz_same_call_uuid;// 当前通道呼入或呼出所对应的外线号码 (自定义)
    private String variable_zhuz_vas_gateway;   // 当前通道呼入或呼出所对应的外线号码 (自定义)
    private Boolean channel_HIT_Dialplan;        //"可用于区分主被叫[True+(Call_ANI/Caller_Orig_Caller_ID_Number=Caller_Caller_ID_Number),或者False+(variable_aleg=true)则为主叫]"
    private String caller_ANI;                    //自动识别号码
    private String variable_caller_id;
    private String caller_Username;
    private String caller_Caller_ID_Name;
    private String caller_Caller_ID_Number;        //逻辑主叫号码【当主叫转接后，被叫会变成新的主叫】
    private String caller_Callee_ID_Name;
    private String caller_Callee_ID_Number;        //逻辑被叫号码【该值可能为空】
    private String variable_sip_from_user;        //当前通道的发起者{呼入：手机号码，内部呼叫：分机号，呼出：主叫通道则为分机号、被叫通道则为外线号}
    private String variable_sip_to_user;        //当前通道的承受者{呼入：分机号或者外线号，内部呼叫：分机号，呼出：分机号或手机号}
    private String variable_sip_contact_user;    //当前通道自身的号码
    private String variable_dialed_user;        //如果是主叫通道，这里对应的是第一个被叫分机号码；呼入或内部呼叫时，如果是被叫通道，那该值就等于自身分机号；如果是呼出到手机，情况各异
    private String variable_digits_dialed;        //用户的键
    private String variable_dtmf_type;
    private String variable_dest;                //发起呼叫时我们传递的被叫号码
    private String caller_RDNIS;                //重新自动识别的号码
    private String caller_Destination_Number;    //拨号方案目的号码
    private String variable_dialed_extension;    //拨号方案真实被叫号码
    private String caller_Orig_Caller_ID_Name;
    private String caller_Orig_Caller_ID_Number;//最初发起呼叫的号码
    private Boolean variable_is_exten;            //自定义的判断是否为分机
    private Boolean variable_is_originate;        //自定义
    private Boolean variable_aleg;                //自定义
    private String variable_default_gateway;    //自定义
    private String variable_incoming_gateway_number;    //自定义
    private String variable_init_exten_gateway;    //自定义
    private String variable_ext_field;            //自定义  发起呼叫时传的变量
    private String caller_Context;                //上下文
    private String caller_Direction;            //相对于FreeSWITCH的呼叫方向

    private Date caller_Channel_Created_Time;    //微妙值
    private Date caller_Profile_Created_Time;    //当前通道最后联系的对方通道的创建时间
    private Date caller_Channel_Answered_Time;
    private Date caller_Channel_Bridged_Time;    //注意：该值每次转接，这个bridge时间都会发生变化
    private Date caller_Channel_Hangup_Time;
    private Date event_Date_Local;
    private Date variable_progress_media_stamp;    //接收到远端回铃音的时间
    private Date variable_progress_stamp;        //接收到183回话进行中的响应时间
    private String variable_record_filename;    //录音文件名

    //---时长
    private Integer variable_record_seconds;    //录音时长
    private Integer variable_waitsec;            //通道bridge_create
    private Integer variable_answersec;            //通道create_answer
    private Integer variable_playback_seconds;
    private Integer variable_hold_accum_seconds;
    private Integer variable_progress_mediasec;    //通道create到出现回铃音，经过的时间
    private Integer variable_progresssec;        //通道create到收到183信号，经过的时间
    private Integer variable_billsec;            //从answer开始计时，到hangup结束
    private Integer variable_flow_billsec;        //如果answer了，则从create开始计时，到hangup结束
    private Integer variable_duration;            //通道生存时长

    private String channel_Name;                //通道名称
    private String variable_bridge_channel;        //记录最后一次接通通道。但当前通道挂断时，可能没有建立新的通话【135_》801__转接__>802(没接通)，记录的是801的通道】
    private String other_Leg_Channel_Name;
    private String other_Leg_Unique_ID;
    private String other_Leg_Orig_Caller_ID_Name;
    private String other_Leg_Orig_Caller_ID_Number;
    private String variable_last_bridge_to;
    private String channel_Call_UUID;            //FS用于描述一通通话的ID，测试发现：呼入队列队列{不接通、接通不转接}，以及手机呼入总机并接通时，各通道的channel_call_uuid不相同，其他呼入情况均相同。呼出时，变化各异
    private String unique_ID;                    //当前通道的唯一标识
    private String channel_Presence_ID;            //样式：802@192.168.1.134测试发现：只有分机通道才会有值
    private String variable_a_uuid;                //自定义的变量，只有呼入到队列有值，没什么意义，但保留，作为备用
    private String variable_bridge_uuid;        //记录最后一次接通通道标识【135_》801__转接__>802(没接通)，记录的是801的通道标识】
    private String variable_sip_hangup_disposition;    //挂断/拆线原因【主动挂断，还是被拒绝而挂断】
    private String hangup_Cause;                //挂断原因
    private String variable_last_bridge_hangup_cause;

    private String variable_cc_agent_uuid;        //备用，以后支持转接到队列时，可能需要该值来实现业务
    private String variable_cc_member_uuid;        //备用，以后支持转接到队列时，可能需要该值来实现业务
    private String variable_callcenter_string;    //只有呼入到队列的主叫通道上有值
    private String variable_cc_agent;            //分机Agent
    private String variable_cc_queue;            //队列名称
    private String variable_cc_side;            //是呼入的电话等待成员，还是队列的分机Agent
    private Date variable_cc_queue_answered_epoch;    //进入队列后的answer，秒值
    private Date variable_cc_queue_joined_epoch;    //呼入队列的时间点，秒值

    private String variable_last_app;            //最后调用的APP
    private String variable_last_arg;            //最后调用的APP参数
    private String channel_Read_Codec_Name;
    private String channel_Write_Codec_Name;
    private String variable_presence_id;        //有该值的都是分机通道
    private String variable_sip_user_agent;        //话机类型、或软电话类型
    private String variable_remote_media_ip;    //媒体流输出IP
    private String variable_remote_media_port;
    private String variable_sip_network_ip;        //分机或外线的IP
    private String variable_sip_network_port;
}
