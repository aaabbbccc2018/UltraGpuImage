package com.piasy.ultragpuimage.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import com.piasy.ugi.UgiTextureView;
import com.piasy.ugi.UgiTransformation;
import com.piasy.ugi.utils.Logging;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private static final List<NamedValue> SCALE_TYPE_LIST = Arrays.asList(
            new NamedValue("FIT_XY", UgiTransformation.SCALE_TYPE_FIT_XY),
            new NamedValue("CENTER_CROP", UgiTransformation.SCALE_TYPE_CENTER_CROP),
            new NamedValue("CENTER_INSIDE", UgiTransformation.SCALE_TYPE_CENTER_INSIDE)
    );
    private static final List<NamedValue> FLIP_LIST = Arrays.asList(
            new NamedValue("NONE", UgiTransformation.FLIP_NONE),
            new NamedValue("HORIZONTAL", UgiTransformation.FLIP_HORIZONTAL),
            new NamedValue("VERTICAL", UgiTransformation.FLIP_VERTICAL),
            new NamedValue("HORIZONTAL_VERTICAL", UgiTransformation.FLIP_HORIZONTAL_VERTICAL)
    );
    private static final List<NamedValue> ROTATION_LIST = Arrays.asList(
            new NamedValue("0", UgiTransformation.ROTATION_0),
            new NamedValue("90", UgiTransformation.ROTATION_90),
            new NamedValue("180", UgiTransformation.ROTATION_180),
            new NamedValue("270", UgiTransformation.ROTATION_270)
    );

    @BindView(R.id.surface)
    UgiTextureView surface;

    @BindView(R.id.cropX)
    EditText cropXEdit;
    @BindView(R.id.cropY)
    EditText cropYEdit;
    @BindView(R.id.cropWidth)
    EditText cropWidthEdit;
    @BindView(R.id.cropHeight)
    EditText cropHeightEdit;
    @BindView(R.id.inputWidth)
    EditText inputWidthEdit;
    @BindView(R.id.inputHeight)
    EditText inputHeightEdit;
    @BindView(R.id.outputWidth)
    EditText outputWidthEdit;
    @BindView(R.id.outputHeight)
    EditText outputHeightEdit;

    @BindView(R.id.scaleType)
    Spinner scaleTypeSpinner;
    @BindView(R.id.flip)
    Spinner flipSpinner;
    @BindView(R.id.rotation)
    Spinner rotationSpinner;

    private UgiTransformation mTransformation;

    private int cropX;
    private int cropY;
    private int cropWidth;
    private int cropHeight;
    private int inputWidth;
    private int inputHeight;
    private int outputWidth;
    private int outputHeight;
    private int scaleType;
    private int flip;
    private int rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logging.setLogToConsole(true);

        ButterKnife.bind(this);
        setupNamedValues(scaleTypeSpinner, SCALE_TYPE_LIST);
        setupNamedValues(flipSpinner, FLIP_LIST);
        setupNamedValues(rotationSpinner, ROTATION_LIST);

        cropXEdit.setText("0");
        cropYEdit.setText("0");
        cropWidthEdit.setText("10000");
        cropHeightEdit.setText("10000");
        scaleTypeSpinner.setSelection(0);
        flipSpinner.setSelection(0);
        rotationSpinner.setSelection(0);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.awesomeface);
        inputWidthEdit.setText(String.valueOf(bitmap.getWidth()));
        inputHeightEdit.setText(String.valueOf(bitmap.getHeight()));

        surface.init(null, UgiTextureView.RENDER_MODE_PICTURE);
        mTransformation = surface.getTransformation();
        surface.renderPicture(bitmap);

        surface.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(final SurfaceTexture surface, final int width,
                    final int height) {
                outputWidthEdit.setText(String.valueOf(width));
                outputHeightEdit.setText(String.valueOf(height));
            }

            @Override
            public void onSurfaceTextureSizeChanged(final SurfaceTexture surface, final int width,
                    final int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(final SurfaceTexture surface) {
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(final SurfaceTexture surface) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        surface.destroy();
    }

    @OnTextChanged(R.id.cropX)
    void cropX(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            cropX = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.cropY)
    void cropY(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            cropY = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.cropWidth)
    void cropWidth(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            cropWidth = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.cropHeight)
    void cropHeight(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            cropHeight = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.inputWidth)
    void inputWidth(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            inputWidth = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.inputHeight)
    void inputHeight(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            inputHeight = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.outputWidth)
    void outputWidth(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            outputWidth = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnTextChanged(R.id.outputHeight)
    void outputHeight(CharSequence text, int start, int count, int after) {
        if (!TextUtils.isEmpty(text)) {
            outputHeight = Integer.parseInt(text.toString());
            updateTransformation();
        }
    }

    @OnItemSelected(R.id.scaleType)
    void scaleType(AdapterView<?> parent, View view, int position, long id) {
        scaleType = SCALE_TYPE_LIST.get(position).value;
        updateTransformation();
    }

    @OnItemSelected(R.id.flip)
    void flip(AdapterView<?> parent, View view, int position, long id) {
        flip = FLIP_LIST.get(position).value;
        updateTransformation();
    }

    @OnItemSelected(R.id.rotation)
    void rotation(AdapterView<?> parent, View view, int position, long id) {
        rotation = ROTATION_LIST.get(position).value;
        updateTransformation();
    }

    private void setupNamedValues(Spinner spinner, List<NamedValue> values) {
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                values);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }

    private void updateTransformation() {
        if (mTransformation != null) {
            mTransformation.updateInput(inputWidth, inputHeight);
            mTransformation.updateOutput(outputWidth, outputHeight);
            mTransformation.updateCrop(cropX, cropY, cropWidth, cropHeight);
            mTransformation.updateScaleType(scaleType);
            mTransformation.updateFlip(flip);
            mTransformation.updateRotation(rotation);

            surface.notifyTransformationUpdated();
        }
    }

    private static class NamedValue {
        private final String name;
        private final int value;

        private NamedValue(final String name, final int value) {
            this.name = name;
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }
}
