package com.example.zero2one;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineListadapter extends ArrayAdapter<Timeline> {
   
   Activity activity;
   int layoutResourceId;   
   List<Timeline> data = null;
   
	public TimelineListadapter(Activity activity, int layoutResourceId, List<Timeline> data) {
       super(activity, layoutResourceId, data);
       this.layoutResourceId = layoutResourceId;
       this.activity = activity;
       this.data = data;
       new Thread(new ImageTextPictureUrlLoaderRunnable()).start();
	}

	static class Holder{
		ImageView image;
		TextView txtTitle;
		TextView txtComment;
		ImageView imgLove;
		ImageView imgComment;
		ImageView imgShare;
		ImageView imgMore;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		Holder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new Holder();

			holder.image = (ImageView)row.findViewById(R.id.timelineImage);
			holder.txtTitle = (TextView)row.findViewById(R.id.timelineTitle);
			holder.txtComment = (TextView)row.findViewById(R.id.timelineCommentText);
			holder.imgLove = (ImageView)row.findViewById(R.id.timelineLove);
			holder.imgComment = (ImageView)row.findViewById(R.id.timelineComment);
			holder.imgShare = (ImageView)row.findViewById(R.id.timelineShare);
			holder.imgMore = (ImageView)row.findViewById(R.id.timelineMore);
			
			row.setTag(holder);
		}
		else
		{
			holder = (Holder)row.getTag();
		}
		if(data.get(position)!=null){
			
			if(data.get(position).getBitmap()!=null){
				try{
					holder.image.setImageBitmap(data.get(position).getBitmap());
				}catch(Exception ers){}
			}
			
			holder.txtTitle.setText(data.get(position).getTitle());
			holder.txtComment.setText(data.get(position).getComment());
			
			holder.image.setTag(data.get(position));
			holder.imgComment.setTag(data.get(position));
			holder.imgLove.setTag(data.get(position));
			holder.imgMore.setTag(data.get(position));
			holder.imgShare.setTag(data.get(position));
			
			holder.image.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String postId = ((Timeline)v.getTag()).getPostId();
					Toast.makeText(activity, "Image post="+postId,Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.imgComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String postId = ((Timeline)v.getTag()).getPostId();
					Toast.makeText(activity, "Comment post="+postId,Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.imgLove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String postId = ((Timeline)v.getTag()).getPostId();
					Toast.makeText(activity, "Love post="+postId,Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.imgMore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String postId = ((Timeline)v.getTag()).getPostId();
					Toast.makeText(activity, "More post="+postId,Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.imgShare.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String postId = ((Timeline)v.getTag()).getPostId();
					Toast.makeText(activity, "Share post="+postId,Toast.LENGTH_SHORT).show();
				}
			});
		   //holder.image.setImageResource(data[position].getImageResource());
		   /*holder.txtDescription.setText(data.get(position).getDescription());
		   holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.branco) );
		   
		   MarketingSoloCampaignVO vo = (MarketingSoloCampaignVO) data.get(position).getReferenceObject();
		   if(vo.getMarketingSoloCampaign().getActive() == 1){
			   holder.txtStatus.setText("Campanha ativa");
		   }else{
			   holder.txtStatus.setText("Campanha finalizada");
		   }
		   
		   String sTime = dateFormat.format(new Date(vo.getPublishTime()));
		   holder.txtDate.setText(sTime);*/
		}
		return row;   
	}
	

   public class ImageTextPictureUrlLoaderRunnable implements Runnable{

   	@Override
   	public void run() {
   		for(Timeline im:data){
   			if(im.getBitmap() == null){
   				Bitmap mIcon11 = null;
   				try {
   					InputStream in = new java.net.URL(im.getImageUrl()).openStream();
   					mIcon11 = BitmapFactory.decodeStream(in);
   				} catch (Exception e) {
   					e.printStackTrace();
   				}

   				if(mIcon11!=null){
   					im.setBitmap(mIcon11);
   					activity.runOnUiThread(new Runnable() {
   						@Override
   						public void run() {
   							notifyDataSetChanged();
   						}
   					});
   				}
   			}
   		}

   	}
   }
}