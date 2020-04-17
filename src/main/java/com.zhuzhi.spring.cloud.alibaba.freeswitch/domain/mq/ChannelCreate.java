package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChannelCreate {
    @JSONField(name = "Event-Name")
    private String event_name;

    @JSONField(name = "Core-UUID")
    private String core_uuid;

    @JSONField(name = "FreeSWITCH-Hostname")
    private String freeSWITCH_hostname;

    @JSONField(name = "FreeSWITCH-Switchname")
    private String freeSWITCH_switchname;

    @JSONField(name = "FreeSWITCH-IPv4")
    private String freeSWITCH_iPv4;

    @JSONField(name = "FreeSWITCH-IPv6")
    private String freeSWITCH_iPv6;

    @JSONField(name = "Event-Date-Local")
    private String event_date_local;

    @JSONField(name = "Event-Date-GMT")
    private String event_date_gmt;

    @JSONField(name = "Event-Date-Timestamp")
    private String event_date_timestamp;

    @JSONField(name = "Event-Calling-File")
    private String event_calling_file;

    @JSONField(name = "Event-Calling-Function")
    private String event_calling_function;

    @JSONField(name = "Event-Calling-Line-Number")
    private String event_calling_line_number;

    @JSONField(name = "Event-Sequence")
    private String event_sequence;

    @JSONField(name = "Channel-State")
    private String channel_state;

    @JSONField(name = "Channel-Call-State")
    private String channel_call_state;

    @JSONField(name = "Channel-State-Number")
    private String channel_state_number;

    @JSONField(name = "Channel-Name")
    private String channel_name;

    @JSONField(name = "Unique-ID")
    private String unique_id;

    @JSONField(name = "Call-Direction")
    private String call_direction;

    @JSONField(name = "Presence-Call-Direction")
    private String presence_call_direction;

    @JSONField(name = "Channel-HIT-Dialplan")
    private String channel_hit_dialplan;

    @JSONField(name = "Channel-Presence-ID")
    private String channel_presence_id;

    @JSONField(name = "Channel-Call-UUID")
    private String Channel_call_uuid;

    @JSONField(name = "Answer-State")
    private String answer_state;

    @JSONField(name = "Caller-Direction")
    private String caller_direction;

    @JSONField(name = "Caller-Logical-Direction")
    private String caller_logical_direction;

    @JSONField(name = "Caller-Username")
    private String caller_username;

    @JSONField(name = "Caller-Dialplan")
    private String caller_dialplan;

    @JSONField(name = "Caller-Caller-ID-Name")
    private String caller_caller_id_name;

    @JSONField(name = "Caller-Caller-ID-Number")
    private String caller_caller_id_number;

    @JSONField(name = "Caller-Orig-Caller-ID-Name")
    private String caller_orig_caller_id_name;

    @JSONField(name = "Caller-Orig-Caller-ID-Number")
    private String caller_orig_caller_id_number;

    @JSONField(name = "Caller-Network-Addr")
    private String caller_network_addr;

    @JSONField(name = "Caller-ANI")
    private String caller_ani;

    @JSONField(name = "Caller-Destination-Number")
    private String caller_destination_number;

    @JSONField(name = "Caller-Unique-ID")
    private String caller_unique_id;

    @JSONField(name = "Caller-Source")
    private String caller_source;

    @JSONField(name = "Caller-Context")
    private String caller_context;

    @JSONField(name = "Caller-Channel-Name")
    private String caller_channel_name;

    @JSONField(name = "Caller-Profile-Index")
    private String caller_profile_index;

    @JSONField(name = "Caller-Profile-Created-Time")
    private String caller_profile_created_time;

    @JSONField(name = "Caller-Channel-Created-Time")
    private String caller_channel_created_time;

    @JSONField(name = "Caller-Channel-Answered-Time")
    private String caller_channel_answered_time;

    @JSONField(name = "Caller-Channel-Progress-Time")
    private String caller_channel_progress_time;

    @JSONField(name = "Caller-Channel-Progress-Media-Time")
    private String caller_channel_progress_media_time;

    @JSONField(name = "Caller-Channel-Hangup-Time")
    private String caller_channel_hangup_time;

    @JSONField(name = "Caller-Channel-Transfer-Time")
    private String caller_channel_transfer_time;

    @JSONField(name = "Caller-Channel-Resurrect-Time")
    private String caller_channel_resurrect_time;

    @JSONField(name = "Caller-Channel-Bridged-Time")
    private String caller_channel_bridged_time;

    @JSONField(name = "Caller-Channel-Last-Hold")
    private String caller_channel_last_hold;

    @JSONField(name = "Caller-Channel-Hold-Accum")
    private String caller_channel_hold_accum;

    @JSONField(name = "Caller-Screen-Bit")
    private String caller_screen_bit;

    @JSONField(name = "Caller-Privacy-Hide-Name")
    private String caller_privacy_hide_name;

    @JSONField(name = "Caller-Privacy-Hide-Number")
    private String caller_privacy_hide_number;

    @JSONField(name = "Caller-RDNIS")
    private String caller_rdnis;

    private String variable_direction;

    private String variable_consumer_id;

    private String variable_is_originate;

    private String variable_incoming_gateway_number;

    private String variable_ext_field;

    private String variable_progress_stamp;

    private String variable_a_uuid;

    private String variable_zhuz_same_call_uuid;

    private String variable_cc_agent_uuid;

    private String variable_cc_member_uuid;

    private String variable_callcenter_string;

    private String variable_cc_agent;

    private String variable_cc_queue;

    private String variable_cc_side;

    private String variable_cc_queue_answered_epoch;

    private String variable_cc_queue_joined_epoch;

    private String variable_uuid;

    private String variable_call_uuid;

    private String variable_session_id;

    private String variable_sip_from_user;

    private String variable_sip_from_uri;

    private String variable_dial_type;

    private String variable_dest;

    private String variable_zhuz_vas_gateway;

    private String variable_sip_from_host;

    private String variable_video_media_flow;

    private String variable_audio_media_flow;

    private String variable_text_media_flow;

    private String variable_channel_name;

    private String variable_sip_call_id;

    private String variable_sip_local_network_addr;

    private String variable_sip_network_ip;

    private String variable_sip_network_port;

    private String variable_sip_invite_stamp;

    private String variable_sip_received_ip;

    private String variable_sip_received_port;

    private String variable_sip_via_protocol;

    private String variable_sip_authorized;

    @JSONField(name = "variable_Event-Name")
    private String variable_event_name;

    @JSONField(name = "variable_Core-UUID")
    private String variable_core_uuid;

    @JSONField(name = "variable_FreeSWITCH-Hostname")
    private String variable_freeSWITCH_hostname;

    @JSONField(name = "variable_FreeSWITCH-Switchname")
    private String variable_freeSWITCH_switchname;

    @JSONField(name = "variable_FreeSWITCH-IPv4")
    private String variable_freeSWITCH_ipv4;

    @JSONField(name = "variable_FreeSWITCH-IPv6")
    private String variable_freeSWITCH_ipv6;

    @JSONField(name = "variable_Event-Date-Local")
    private String variable_event_date_local;

    @JSONField(name = "variable_Event-Date-GMT")
    private String variable_event_date_gmt;

    @JSONField(name = "variable_Event-Date-Timestamp")
    private String variable_event_date_timestamp;

    @JSONField(name = "variable_Event-Calling-File")
    private String variable_event_calling_file;

    @JSONField(name = "variable_Event-Calling-Function")
    private String variable_event_calling_function;

    @JSONField(name = "variable_Event-Calling-Line-Number")
    private String variable_event_calling_line_number;

    @JSONField(name = "variable_Event-Sequence")
    private String variable_event_sequence;

    private String variable_sip_number_alias;

    private String variable_sip_auth_username;

    private String variable_sip_auth_realm;

    private String variable_number_alias;

    private String variable_requested_user_name;

    private String variable_requested_domain_name;

    private String variable_record_stereo;

    private String variable_default_gateway;

    private String variable_default_areacode;

    private String variable_transfer_fallback_extension;

    private String variable_effective_caller_id_name;

    private String variable_effective_caller_id_number;

    private String variable_outbound_caller_id_name;

    private String variable_outbound_caller_id_number;

    private String variable_callgroup;

    @JSONField(name = "variable_sip-force-expires")
    private String variable_sip_force_expires;

    private String variable_is_exten;

    private String variable_user_name;

    private String variable_domain_name;

    private String variable_sip_from_user_stripped;

    private String variable_sip_from_tag;

    private String variable_sofia_profile_name;

    private String variable_sofia_profile_url;

    private String variable_recovery_profile_name;

    private String variable_sip_invite_route_uri;

    private String variable_sip_invite_record_route;

    private String variable_sip_full_via;

    private String variable_sip_recover_via;

    private String variable_sip_full_from;

    private String variable_sip_full_to;

    private String variable_sip_allow;

    private String variable_sip_req_user;

    private String variable_sip_req_port;

    private String variable_sip_req_uri;

    private String variable_sip_req_host;

    private String variable_sip_to_user;

    private String variable_sip_to_uri;

    private String variable_sip_to_host;

    private String variable_sip_contact_params;

    private String variable_sip_contact_user;

    private String variable_sip_contact_port;

    private String variable_sip_contact_uri;

    private String variable_sip_contact_host;

    private String variable_rtp_use_codec_string;

    private String variable_sip_user_agent;

    private String variable_sip_via_host;

    private String variable_sip_via_port;

    private String variable_max_forwards;

    private String variable_presence_id;

    private String variable_switch_r_sdp;

    private String variable_ep_codec_string;

    private String variable_endpoint_disposition;

}
