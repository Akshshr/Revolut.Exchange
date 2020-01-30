package com.revolutexchange.app.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.revolutexchange.R;
import com.revolutexchange.databinding.ViewToolbarBinding;

public class MainToolbar extends FrameLayout{

    private ViewToolbarBinding binding;

    public MainToolbar(Context context) {
        super(context);
        init();
    }

    public MainToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_toolbar, this, true);
    }

    public Toolbar getToolbar() {
        return binding.viewToolbar;
    }

    public void setTitle(String title) {
        if (title == null || title.equals("")) {
            binding.toolbarTitle.setVisibility(INVISIBLE);
        } else {
            binding.toolbarTitle.setText(title);
        }
    }


    public void setRightToolbarIcon(Drawable toolbarIcon) {
        if (toolbarIcon == null) {
            binding.rightIcon.setVisibility(GONE);
        }
        binding.rightIcon.setVisibility(VISIBLE);
        binding.rightIcon.setBackground(toolbarIcon);
    }

    public View getRightToolbarIcon() {
        return binding.rightIcon;
    }


    public void setLeftToolbarIcon(Drawable toolbarIcon) {
        if (toolbarIcon == null) {
            binding.leftIcon.setVisibility(GONE);
        }
        binding.leftIcon.setVisibility(VISIBLE);
        binding.leftIcon.setBackground(toolbarIcon);
    }


    public View getLeftToolbarIcon() {
        return binding.leftIcon;
    }

    public void setActionkey(String actionKeyTitle) {
        binding.actionKey.setVisibility(VISIBLE);
        binding.actionKey.setText(actionKeyTitle);
    }

    public View getActionkey() {
        return binding.actionKey;
    }
}
