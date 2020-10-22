package com.example.dialogmachine;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


//聊天信息的适配器
public class ChatMessageAdapter extends BaseAdapter {

    private List<ChatMessage> list;

    public ChatMessageAdapter(List<ChatMessage> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.isEmpty()?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = list.get(position);
        // 接收消息：0，发送消息：1
        if (chatMessage.getType() == ChatMessage.Type.INCOUNT) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = list.get(position);
        if (convertView == null){
            ViewHolder viewHolder = null;
            //加载布局
            if (getItemViewType(position)==0){
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.left_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.time = (TextView) convertView.findViewById(R.id.left_time);
                viewHolder.msg = (TextView) convertView.findViewById(R.id.left_message);
            }else{
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.time = (TextView) convertView.findViewById(R.id.right_time);
                viewHolder.msg = (TextView) convertView.findViewById(R.id.right_message);
            }
            convertView.setTag(viewHolder);
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.time.setText(chatMessage.getDate().toString());
        vh.msg.setText(chatMessage.getMessage());
        return convertView;
    }

    private class ViewHolder{
        private TextView time,msg;
    }
}
