package io.github.rellimn.rhopdf;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.artifex.mupdf.viewer.DocumentActivity;

public class LaunchActivity extends Activity
{
	protected final int FILE_REQUEST = 42;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		ComponentName componentToDisable =
				new ComponentName("io.github.rellimn.rhopdf",
						"io.github.rellimn.rhopdf.LaunchActivity");

		getPackageManager().setComponentEnabledSetting(
				componentToDisable,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}

	public void onActivityResult(int request, int result, Intent data) {
		if (request == FILE_REQUEST && result == Activity.RESULT_OK) {
			if (data != null) {
				Intent intent = new Intent(this, DocumentActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
				intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(data.getData(), data.getType());
				intent.putExtra(getComponentName().getPackageName() + ".ReturnToLibraryActivity", 1);
				startActivity(intent);
			}
			finish();
		} else if (request == FILE_REQUEST && result == Activity.RESULT_CANCELED) {
			finish();
		}
	}
}
