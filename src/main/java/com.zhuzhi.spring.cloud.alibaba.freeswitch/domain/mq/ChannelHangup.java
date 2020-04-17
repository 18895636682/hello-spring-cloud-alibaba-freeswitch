package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ChannelHangup {

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

    @JSONField(name = "Hangup-Cause")
    private String hangup_cause;

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

    @JSONField(name = "Channel-Read-Codec-Name")
    private String channel_read_codec_name;

    @JSONField(name = "Channel-Read-Codec-Rate")
    private String channel_read_codec_rate;

    @JSONField(name = "Channel-Read-Codec-Bit-Rate")
    private String channel_read_codec_bit_rate;

    @JSONField(name = "Channel-Write-Codec-Name")
    private String channel_write_codec_name;

    @JSONField(name = "Channel-Write-Codec-Rate")
    private String channel_write_codec_rate;

    @JSONField(name = "Channel-Write-Codec-Bit-Rate")
    private String channel_write_codec_bit_rate;

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

    @JSONField(name = "Caller-Callee-ID-Name")
    private String caller_callee_id_name;

    @JSONField(name = "Caller-Callee-ID-Number")
    private String caller_callee_id_number;

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

    @JSONField(name = "Other-Type")
    private String other_type;

    @JSONField(name = "Other-Leg-Direction")
    private String other_leg_direction;

    @JSONField(name = "Other-Leg-Logical-Direction")
    private String other_leg_logical_direction;

    @JSONField(name = "Other-Leg-Username")
    private String other_leg_username;

    @JSONField(name = "Other-Leg-Dialplan")
    private String other_leg_dialplan;

    @JSONField(name = "Other-Leg-Caller-ID-Name")
    private String other_leg_caller_id_name;

    @JSONField(name = "Other-Leg-Caller-ID-Number")
    private String other_leg_caller_id_number;

    @JSONField(name = "Other-Leg-Orig-Caller-ID-Name")
    private String other_leg_orig_caller_id_name;

    @JSONField(name = "Other-Leg-Orig-Caller-ID-Number")
    private String other_leg_orig_caller_id_number;

    @JSONField(name = "Other-Leg-Callee-ID-Name")
    private String other_leg_callee_id_name;

    @JSONField(name = "Other-Leg-Callee-ID-Number")
    private String other_leg_callee_id_number;

    @JSONField(name = "Other-Leg-Network-Addr")
    private String other_leg_network_addr;

    @JSONField(name = "Other-Leg-ANI")
    private String other_leg_ani;

    @JSONField(name = "Other-Leg-Destination-Number")
    private String other_leg_destination_number;

    @JSONField(name = "Other-Leg-Unique-ID")
    private String other_leg_unique_id;

    @JSONField(name = "Other-Leg-Source")
    private String other_leg_source;

    @JSONField(name = "Other-Leg-Context")
    private String other_leg_context;

    @JSONField(name = "Other-Leg-Channel-Name")
    private String other_leg_channel_name;

    @JSONField(name = "Other-Leg-Profile-Created-Time")
    private String other_leg_profile_created_time;

    @JSONField(name = "Other-Leg-Channel-Created-Time")
    private String other_leg_channel_created_time;

    @JSONField(name = "Other-Leg-Channel-Answered-Time")
    private String other_leg_channel_answered_time;

    @JSONField(name = "Other-Leg-Channel-Progress-Time")
    private String other_leg_channel_progress_time;

    @JSONField(name = "Other-Leg-Channel-Progress-Media-Time")
    private String other_leg_channel_progress_media_time;

    @JSONField(name = "Other-Leg-Channel-Hangup-Time")
    private String other_leg_channel_hangup_time;

    @JSONField(name = "Other-Leg-Channel-Transfer-Time")
    private String other_leg_channel_transfer_time;

    @JSONField(name = "Other-Leg-Channel-Resurrect-Time")
    private String other_leg_channel_resurrect_time;

    @JSONField(name = "Other-Leg-Channel-Bridged-Time")
    private String other_leg_channel_bridged_time;

    @JSONField(name = "Other-Leg-Channel-Last-Hold")
    private String other_leg_channel_last_hold;

    @JSONField(name = "Other-Leg-Channel-Hold-Accum")
    private String other_leg_channel_hold_accum;

    @JSONField(name = "Other-Leg-Screen-Bit")
    private String other_leg_screen_bit;

    @JSONField(name = "Other-Leg-Privacy-Hide-Name")
    private String other_leg_privacy_hide_name;

    @JSONField(name = "Other-Leg-Privacy-Hide-Number")
    private String other_leg_privacy_hide_number;

    private String variable_direction;

    private String variable_consumer_id;

    private String variable_uuid;

    private String variable_session_id;

    private String variable_sip_from_user;

    private String variable_sip_from_uri;

    private String variable_sip_from_host;

    private String variable_video_media_flow;

    private String variable_text_media_flow;

    private String variable_channel_name;

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
    private String variable_dvent_date_local;

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

    @JSONField(name = "Caller-RDNIS")
    private String caller_rdnis;

    private String variable_sip_number_alias;

    private String variable_is_originate;

    private String variable_a_uuid;

    private String variable_last_bridge_hangup_cause;

    private String variable_cc_agent_uuid;

    private String variable_cc_member_uuid;

    private String variable_callcenter_string;

    private String variable_cc_agent;

    private String variable_cc_queue;

    private String variable_cc_side;

    private String variable_cc_queue_answered_epoch;

    private String variable_cc_queue_joined_epoch;

    private String variable_incoming_gateway_number;

    private String variable_ext_field;

    private String variable_record_seconds;

    private String variable_progress_stamp;

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

    private String variable_sofia_profile_name;

    private String variable_sofia_profile_url;

    private String variable_recovery_profile_name;

    private String variable_sip_invite_route_uri;

    private String variable_sip_invite_record_route;

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

    private String variable_sip_via_host;

    private String variable_sip_via_port;

    private String variable_max_forwards;

    private String variable_presence_id;

    private String variable_switch_r_sdp;

    private String variable_ep_codec_string;

    private String variable_zhuz_same_call_uuid;

    private String variable_zhuz_orig_dest;

    private String variable_dial_type;

    private String variable_dest;

    private String variable_zhuz_vas_gateway;

    private String variable_bridge_pre_execute_aleg_app;

    private String variable_bridge_pre_execute_aleg_data;

    private List<String> variable_DP_MATCH;

    private String variable_call_uuid;

    private String variable_rtp_payload_number;

    private String variable_init_exten_timeout;

    private String variable_init_exten_gateway;

    private String variable_init_gateway_timeout;

    private String variable_cc_export_vars;

    private String variable_RFC2822_DATE;

    private String variable_dialed_extension;

    private String variable_bind_meta_key;

    private String variable_transfer_ringback;

    private String variable_continue_on_fail;

    private String variable_hangup_after_bridge;

    private String variable_rtp_use_codec_string;

    private String variable_remote_audio_media_flow;

    private String variable_rtp_remote_audio_rtcp_port;

    private String variable_rtp_audio_recv_pt;

    private String variable_rtp_use_codec_name;

    private String variable_rtp_use_codec_rate;

    private String variable_rtp_use_codec_ptime;

    private String variable_rtp_use_codec_channels;

    private String variable_rtp_last_audio_codec_string;

    private String variable_read_codec;

    private String variable_original_read_codec;

    private String variable_read_rate;

    private String variable_original_read_rate;

    private String variable_write_codec;

    private String variable_write_rate;

    private String variable_dtmf_type;

    private String variable_local_media_ip;

    private String variable_local_media_port;

    private String variable_advertised_media_ip;

    private String variable_rtp_use_timer_name;

    private String variable_rtp_use_pt;

    private String variable_rtp_use_ssrc;

    private String variable_rtp_2833_send_payload;

    private String variable_rtp_2833_recv_payload;

    private String variable_remote_media_ip;

    private String variable_remote_media_port;

    private String variable_recording_follow_transfer;

    private String variable_randomnum;

    private String variable_filename;

    private String variable_record_filename;

    @JSONField(name = "variable_nolocal:execute_on_answer")
    private String variable_nolocal_execute_on_answer;

    private String variable_export_vars;

    private String variable_dialed_user;

    private String variable_dialed_domain;

    private List<String> variable_originated_legs;

    private String variable_aleg;

    private String variable_switch_m_sdp;

    private List<String> variable_originate_causes;

    private String variable_audio_media_flow;

    private String variable_rtp_local_sdp_str;

    private String variable_endpoint_disposition;

    private String variable_originate_disposition;

    private String variable_DIALSTATUS;

    private String variable_callee_number;

    private String variable_last_bridge_to;

    private String variable_bridge_channel;

    private String variable_bridge_uuid;

    private String variable_signal_bond;

    private String variable_bridge_pre_execute_app;

    private String variable_bridge_pre_execute_data;

    private String variable_sip_to_tag;

    private String variable_sip_from_tag;

    private String variable_sip_cseq;

    private String variable_sip_call_id;

    private String variable_sip_full_route;

    private String variable_sip_full_via;

    private String variable_sip_recover_via;

    private String variable_sip_from_display;

    private String variable_sip_full_from;

    private String variable_sip_full_to;

    private String variable_last_sent_callee_id_name;

    private String variable_last_sent_callee_id_number;

    private String variable_current_application_data;

    private String variable_current_application;

    private String variable_playback_last_offset_pos;

    private String variable_playback_seconds;

    private String variable_playback_ms;

    private String variable_playback_samples;

    private String variable_current_application_response;

    private String variable_sip_term_status;

    private String variable_proto_specific_hangup_cause;

    private String variable_sip_term_cause;

    private String variable_last_bridge_role;

    private String variable_sip_user_agent;

    private String variable_sip_hangup_disposition;

    private String variable_bridge_hangup_cause;

    private String variable_hangup_cause;

    private String variable_hangup_cause_q850;

    private String variable_digits_dialed;

    private String variable_start_stamp;

    private String variable_profile_start_stamp;

    private String variable_answer_stamp;

    private String variable_bridge_stamp;

    private String variable_progress_media_stamp;

    private String variable_end_stamp;

    private String variable_start_epoch;

    private String variable_start_uepoch;

    private String variable_profile_start_epoch;

    private String variable_profile_start_uepoch;

    private String variable_answer_epoch;

    private String variable_answer_uepoch;

    private String variable_bridge_epoch;

    private String variable_bridge_uepoch;

    private String variable_last_hold_epoch;

    private String variable_last_hold_uepoch;

    private String variable_hold_accum_seconds;

    private String variable_hold_accum_usec;

    private String variable_hold_accum_ms;

    private String variable_resurrect_epoch;

    private String variable_resurrect_uepoch;

    private String variable_progress_epoch;

    private String variable_progress_uepoch;

    private String variable_progress_media_epoch;

    private String variable_progress_media_uepoch;

    private String variable_end_epoch;

    private String variable_end_uepoch;

    private String variable_last_app;

    private String variable_last_arg;

    private String variable_caller_id;

    private String variable_duration;

    private String variable_billsec;

    private String variable_progresssec;

    private String variable_answersec;

    private String variable_waitsec;

    private String variable_progress_mediasec;

    private String variable_flow_billsec;

    private String variable_mduration;

    private String variable_billmsec;

    private String variable_progressmsec;

    private String variable_answermsec;

    private String variable_waitmsec;

    private String variable_progress_mediamsec;

    private String variable_flow_billmsec;

    private String variable_uduration;

    private String variable_billusec;

    private String variable_progressusec;

    private String variable_answerusec;

    private String variable_waitusec;

    private String variable_progress_mediausec;

    private String variable_flow_billusec;

    private String variable_rtp_audio_in_raw_bytes;

    private String variable_rtp_audio_in_media_bytes;

    private String variable_rtp_audio_in_packet_count;

    private String variable_rtp_audio_in_media_packet_count;

    private String variable_rtp_audio_in_skip_packet_count;

    private String variable_rtp_audio_in_jitter_packet_count;

    private String variable_rtp_audio_in_dtmf_packet_count;

    private String variable_rtp_audio_in_cng_packet_count;

    private String variable_rtp_audio_in_flush_packet_count;

    private String variable_rtp_audio_in_largest_jb_size;

    private String variable_rtp_audio_in_jitter_min_variance;

    private String variable_rtp_audio_in_jitter_max_variance;

    private String variable_rtp_audio_in_jitter_loss_rate;

    private String variable_rtp_audio_in_jitter_burst_rate;

    private String variable_rtp_audio_in_mean_interval;

    private String variable_rtp_audio_in_flaw_total;

    private String variable_rtp_audio_in_quality_percentage;

    private String variable_rtp_audio_in_mos;

    private String variable_rtp_audio_out_raw_bytes;

    private String variable_rtp_audio_out_media_bytes;

    private String variable_rtp_audio_out_packet_count;

    private String variable_rtp_audio_out_media_packet_count;

    private String variable_rtp_audio_out_skip_packet_count;

    private String variable_rtp_audio_out_dtmf_packet_count;

    private String variable_rtp_audio_out_cng_packet_count;

    private String variable_rtp_audio_rtcp_packet_count;

    private String variable_rtp_audio_rtcp_octet_count;
}
