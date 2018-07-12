package com.example.shaun.chauma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Accept or decline a request to join an event set by the co-ordinator.
 * If a decline, the peer educator must send a message as to why they are declining.
 *
 * @author  Jacob de Beer
 * @version 1.0
 * @since   2018-01-31
 */
public class PeerEducatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_educator_activity);
    }
}
