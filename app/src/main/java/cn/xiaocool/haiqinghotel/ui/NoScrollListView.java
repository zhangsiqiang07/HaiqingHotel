package cn.xiaocool.haiqinghotel.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wzh on 2016/3/14.
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context, AttributeSet attrs){
        super(context,attrs);

    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
