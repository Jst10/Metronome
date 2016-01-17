package com.jst10.by.made.metronome.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by jst10 on 17.1.2016.
 */
public class CustomEditText extends EditText {

    public interface  EditingListener{
        void onDoneEditing();
    }

    private EditingListener editingListener;
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEditingListener(EditingListener editingListener) {
        this.editingListener = editingListener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if(editingListener!=null){
                editingListener.onDoneEditing();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }


}
