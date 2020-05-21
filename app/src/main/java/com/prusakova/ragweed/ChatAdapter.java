package com.prusakova.ragweed;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.model.Chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<Chat> chatRoomList;
    private static String today;
    private RecyclerViewClickListener mListener;

    public ChatAdapter(List<Chat> chatRoomArrayList, Context mContext, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.chatRoomList = chatRoomArrayList;
        this.mListener = listener;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chats, parent, false);

        return new ViewHolder(itemView,mListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chatRoom = chatRoomList.get(position);
        holder.name.setText(chatRoom.getChatName());
        holder.message.setText(chatRoom.getLastMessage());
        if (chatRoom.getUnreadCount() > 0) {
            holder.count.setText(String.valueOf(chatRoom.getUnreadCount()));
            holder.count.setVisibility(View.VISIBLE);
        } else {
            holder.count.setVisibility(View.GONE);
        }

        holder.timestamp.setText(getTimeStamp(chatRoom.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, message, timestamp, count;
        private FrameLayout mRowContainer;
        public ViewHolder(View view, ChatAdapter.RecyclerViewClickListener listener) {
            super(view);
            name =  view.findViewById(R.id.chat_name);
            message = (TextView) view.findViewById(R.id.last_message);
            timestamp = (TextView) view.findViewById(R.id.timestamp_chat);
            count = (TextView) view.findViewById(R.id.count_mess);
            mRowContainer = itemView.findViewById(R.id.chat_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chat_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);

    }




    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

//    public interface ClickListener {
//        void onClick(View view, int position);
//
//        void onLongClick(View view, int position);
//    }

//    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//
//        private GestureDetector gestureDetector;
//        private ChatAdapter.ClickListener clickListener;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ChatAdapter.ClickListener clickListener) {
//            this.clickListener = clickListener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//                clickListener.onClick(child, rv.getChildPosition(child));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    }

}
