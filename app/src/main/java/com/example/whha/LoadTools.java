package com.example.whha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;

public class LoadTools extends Activity implements DialogInterface.OnKeyListener {

	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 展示对话框
	public void showLoadingDialog() {
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
		alertDialog.setCancelable(false);
		alertDialog.setOnKeyListener(this);

		alertDialog.show();
		alertDialog.getWindow().setLayout(300, 300);
		alertDialog.setContentView(R.layout.loading_alert);
		alertDialog.setCanceledOnTouchOutside(false);
	}

	// 退出对话框
	public void dismissLoadingDialog() {
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
		}
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return false;
		}
	}
}
