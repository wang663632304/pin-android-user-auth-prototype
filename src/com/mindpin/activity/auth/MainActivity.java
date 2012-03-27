package com.mindpin.activity.auth;

import com.mindpin.R;
import com.mindpin.base.activity.MindpinBaseActivity;
import com.mindpin.cache.image.ImageCache;
import com.mindpin.logic.AccountManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends MindpinBaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        update_account_info();
    }

    // 在界面上刷新头像和用户名
    private void update_account_info() {
        TextView account_name_textview = (TextView) findViewById(R.id.account_name);
        ImageView account_avatar_imgview = (ImageView) findViewById(R.id.account_avatar);

        account_name_textview.setText(current_user().name);
        ImageCache.load_cached_image(current_user().avatar_url,
                account_avatar_imgview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_logout:
            AccountManager.logout();
            restart_to_login();
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}