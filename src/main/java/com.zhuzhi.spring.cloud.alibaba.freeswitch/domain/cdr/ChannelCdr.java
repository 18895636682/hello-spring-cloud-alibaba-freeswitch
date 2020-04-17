package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "channel_cdr")
public class ChannelCdr implements Serializable {
    private static final long serialVersionUID = -3002677449769464723L;

    @Column(name = "id")
    private String id;

    @Column(name = "caller_channel_created_time")
    private Date caller_channel_created_time;

    @Column(name = "directiontype")
    private Integer directiontype;

    @Column(name = "dialtype")
    private Integer dialtype;

    @Column(name = "isCaller")
    private Boolean isCaller;

    @Column(name = "variable_zhuz_same_call_uuid")
    private String variable_zhuz_same_call_uuid;

    @Column(name = "variable_zhuz_vas_gateway")
    private String variable_zhuz_vas_gateway;

    @Column(name = "channel_hit_dialplan")
    private Boolean channel_hit_dialplan;

    @Column(name = "caller_ani")
    private String caller_ani;

    @Column(name = "variable_caller_id")
    private String variable_caller_id;

    @Column(name = "caller_username")
    private String caller_username;

    @Column(name = "caller_caller_id_name")
    private String caller_caller_id_name;

    @Column(name = "caller_caller_id_number")
    private String caller_caller_id_number;

    @Column(name = "caller_callee_id_name")
    private String caller_callee_id_name;

    @Column(name = "caller_callee_id_number")
    private String caller_callee_id_number;

    @Column(name = "variable_sip_from_user")
    private String variable_sip_from_user;

    @Column(name = "variable_sip_to_user")
    private String variable_sip_to_user;

    @Column(name = "variable_sip_contact_user")
    private String variable_sip_contact_user;

    @Column(name = "variable_dialed_user")
    private String variable_dialed_user;

    @Column(name = "variable_digits_dialed")
    private String variable_digits_dialed;

    @Column(name = "variable_dtmf_type")
    private String variable_dtmf_type;

    @Column(name = "variable_dest")
    private String variable_dest;

    @Column(name = "caller_rdnis")
    private String caller_rdnis;

    @Column(name = "caller_destination_number")
    private String caller_destination_number;

    @Column(name = "variable_dialed_extension")
    private String variable_dialed_extension;

    @Column(name = "caller_orig_caller_id_name")
    private String caller_orig_caller_id_name;

    @Column(name = "caller_orig_caller_id_number")
    private String caller_orig_caller_id_number;

    @Column(name = "variable_default_gateway")
    private String variable_default_gateway;

    @Column(name = "variable_incoming_gateway_number")
    private String variable_incoming_gateway_number;

    @Column(name = "variable_init_exten_gateway")
    private String variable_init_exten_gateway;

    @Column(name = "variable_ext_field")
    private String variable_ext_field;

    @Column(name = "caller_context")
    private String caller_context;

    @Column(name = "caller_direction")
    private String caller_direction;

    @Column(name = "caller_profile_created_time")
    private Date caller_profile_created_time;

    @Column(name = "caller_channel_answered_time")
    private Date caller_channel_answered_time;

    @Column(name = "caller_channel_bridged_time")
    private Date caller_channel_bridged_time;

    @Column(name = "caller_channel_hangup_time")
    private Date caller_channel_hangup_time;

    @Column(name = "event_date_local")
    private Date event_date_local;

    @Column(name = "variable_progress_media_stamp")
    private Date variable_progress_media_stamp;

    @Column(name = "variable_progress_stamp")
    private Date variable_progress_stamp;

    @Column(name = "variable_record_filename")
    private String variable_record_filename;

    @Column(name = "channel_name")
    private String channel_name;

    @Column(name = "variable_bridge_channel")
    private String variable_bridge_channel;

    @Column(name = "other_leg_channel_name")
    private String other_leg_channel_name;

    @Column(name = "other_leg_unique_id")
    private String other_leg_unique_id;

    @Column(name = "other_leg_orig_caller_id_name")
    private String other_leg_orig_caller_id_name;

    @Column(name = "other_leg_orig_caller_id_number")
    private String other_leg_orig_caller_id_number;

    @Column(name = "variable_last_bridge_to")
    private String variable_last_bridge_to;

    @Column(name = "channel_call_uuid")
    private String channel_call_uuid;

    @Column(name = "unique_id")
    private String unique_id;

    @Column(name = "channel_presence_id")
    private String channel_presence_id;

    @Column(name = "variable_a_uuid")
    private String variable_a_uuid;

    @Column(name = "variable_bridge_uuid")
    private String variable_bridge_uuid;

    @Column(name = "variable_sip_hangup_disposition")
    private String variable_sip_hangup_disposition;

    @Column(name = "hangup_cause")
    private String hangup_cause;

    @Column(name = "variable_last_bridge_hangup_cause")
    private String variable_last_bridge_hangup_cause;

    @Column(name = "variable_cc_agent_uuid")
    private String variable_cc_agent_uuid;

    @Column(name = "variable_cc_member_uuid")
    private String variable_cc_member_uuid;

    @Column(name = "variable_callcenter_string")
    private String variable_callcenter_string;

    @Column(name = "variable_cc_agent")
    private String variable_cc_agent;

    @Column(name = "variable_cc_queue")
    private String variable_cc_queue;

    @Column(name = "variable_cc_side")
    private String variable_cc_side;

    @Column(name = "variable_cc_queue_answered_epoch")
    private Date variable_cc_queue_answered_epoch;

    @Column(name = "variable_cc_queue_joined_epoch")
    private Date variable_cc_queue_joined_epoch;

    @Column(name = "variable_last_app")
    private String variable_last_app;

    @Column(name = "variable_last_arg")
    private String variable_last_arg;

    @Column(name = "channel_read_codec_name")
    private String channel_read_codec_name;

    @Column(name = "channel_write_codec_name")
    private String channel_write_codec_name;

    @Column(name = "variable_presence_id")
    private String variable_presence_id;

    @Column(name = "variable_sip_user_agent")
    private String variable_sip_user_agent;

    @Column(name = "variable_remote_media_ip")
    private String variable_remote_media_ip;

    @Column(name = "variable_remote_media_port")
    private String variable_remote_media_port;

    @Column(name = "variable_sip_network_ip")
    private String variable_sip_network_ip;

    @Column(name = "variable_sip_network_port")
    private String variable_sip_network_port;

    @Column(name = "variable_is_exten")
    private Boolean variable_is_exten;

    @Column(name = "variable_is_originate")
    private Boolean variable_is_originate;

    @Column(name = "variable_aleg")
    private Boolean variable_aleg;

    @Column(name = "variable_record_seconds")
    private Integer variable_record_seconds;

    @Column(name = "variable_waitsec")
    private Integer variable_waitsec;

    @Column(name = "variable_answersec")
    private Integer variable_answersec;

    @Column(name = "variable_playback_seconds")
    private Integer variable_playback_seconds;

    @Column(name = "variable_hold_accum_seconds")
    private Integer variable_hold_accum_seconds;

    @Column(name = "variable_progress_mediasec")
    private Integer variable_progress_mediasec;

    @Column(name = "variable_progresssec")
    private Integer variable_progresssec;

    @Column(name = "variable_billsec")
    private Integer variable_billsec;

    @Column(name = "variable_flow_billsec")
    private Integer variable_flow_billsec;

    @Column(name = "variable_duration")
    private Integer variable_duration;

    @Column(name = "variable_consumer_id")
    private String variable_consumer_id;
}