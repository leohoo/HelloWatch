package com.easyjp.hellowatch;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Locale;


public class MyActivity extends Activity implements NotiFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new NotiFragment();
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            TextView label = (TextView) rootView.findViewById(R.id.section_label);
            Bundle bundle = getArguments();
            int id = bundle.getInt(ARG_SECTION_NUMBER);
            label.setText(String.format("page %d", id));
            return rootView;
        }
    }

    public void onFragmentInteraction(Uri uri) {

    }

    public void sendNoti(View btn) {
        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, MyActivity.class);
        viewIntent.putExtra("EXTRA_EVENT_ID", notificationId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Hello")
                        .setContentText("Hello, Watch!")
                        .setContentIntent(viewPendingIntent);

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=shibuya");
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);


        // Create an intent for the reply action
        Intent actionIntent = new Intent(this, MyActivity.class);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                        getString(R.string.app_name), actionPendingIntent)
                        .build();

        NotificationCompat.Action mapAction = new Builder(android.R.drawable.ic_dialog_map, "Map", mapPendingIntent).build();

        notificationBuilder.addAction(action).addAction(mapAction);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public void sendWatchOnlyNoti(View btn) {
        int notificationId = 002;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, MyActivity.class);
        viewIntent.putExtra("EXTRA_EVENT_ID", notificationId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Hello")
                        .setContentText("Hello, Watch!")
                        .setContentIntent(viewPendingIntent);

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=shibuya");
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);


        // Create an intent for the reply action
        Intent actionIntent = new Intent(this, MyActivity.class);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher,
                        getString(R.string.app_name), actionPendingIntent)
                        .build();

        NotificationCompat.Action mapAction = new Builder(android.R.drawable.ic_dialog_map, "Map", mapPendingIntent).build();

        notificationBuilder.extend(new NotificationCompat.WearableExtender().addActions(Arrays.asList(new NotificationCompat.Action[]{action, mapAction})));

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public void addBigView(View btn) {
        int notificationId = 3;
        // Specify the 'big view' content to display the long
        // event description that may not fit the normal content text.
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();

        bigStyle.bigText("Add big view test\n" +
                "big view text\n" +
                "big view text\n" +
                "big view text\n" +
                "big view text\n" +
                "big view text\n" +
                "big view text");
        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=shibuya");
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.football))
                        .setContentTitle("big view")
                        .setContentIntent(mapPendingIntent)
                        .addAction(android.R.drawable.ic_dialog_map,
                                "map", mapPendingIntent)
                        .setStyle(bigStyle);

        notificationBuilder.setVibrate(new long[]{0, 100, 200, 100, 500, 100});

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public void addWearableFeatures(View btn) {
        int notificationId = 4;

// Create a WearableExtender to add functionality for wearables
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Extra Page")
                .setContentText("text\n" +
                        "text\n" +
                        "text\n" +
                        "text\n" +
                        "text\n" +
                        "text\n" +
                        "text\n" +
                        "text\n");

        Notification page1 = notiBuilder.build();
        Notification page2 = notiBuilder.setContentTitle("Extra Page 2").build();

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.brazil);
        wearableExtender.addPages(Arrays.asList(new Notification[]{page1, page2})).setBackground(background);

        wearableExtender.setHintShowBackgroundOnly(true);

// Create a NotificationCompat.Builder to build a standard notification
// then extend it with the WearableExtender
        Notification notif = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from " + "Eric")
                .setContentText("Hello, Mountain View")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .extend(wearableExtender)
                .build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notif);
    }

    public void receivingVoiceInput(View v) {
        int notificationId = 5;

        // Key for the string that's delivered in the action's intent
        final String EXTRA_VOICE_REPLY = "extra_voice_reply";

        String replyLabel = "Would u like some coffee?";

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(getResources().getStringArray(R.array.reply_choices))
                .build();

// Create an intent for the reply action
        Intent replyIntent = new Intent(this, ReplyActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

// Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "Reply", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

// Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_plusone_medium_off_client)
                        .setContentTitle("Title")
                        .setContentText("content")
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .build();

// Issue the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);
    }

    public void stackingNotifications(View v) {
        final String GROUP_KEY_EMAILS = "group_key_emails";
        final int notificationId = 6;

// Build the notification, setting the group appropriately
        Notification notif = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from " + "Cook")
                .setContentText("This watch is really cool!")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setGroup(GROUP_KEY_EMAILS)
                .build();

// Issue the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notif);

        Notification notif2 = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from " + "Cook")
                .setContentText("I want a G watch too.")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setGroup(GROUP_KEY_EMAILS)
                .build();

        notificationManager.notify(5, notif2);
    }
}