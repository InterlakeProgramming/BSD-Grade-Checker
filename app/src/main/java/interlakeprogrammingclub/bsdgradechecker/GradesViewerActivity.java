package interlakeprogrammingclub.bsdgradechecker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GradesViewerActivity extends FragmentActivity {

    private SharedPreferences gradesData;
    private ViewPager viewPager;
    private GradesAdapter gradeAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_viewer);
        gradesData = getSharedPreferences("grades", MODE_PRIVATE);
        gradeAdapter = new GradesAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.avgGradeViewerPager);
        viewPager.setAdapter(gradeAdapter);

    }

    private static class GradesAdapter extends FragmentPagerAdapter{

        private GradesViewerActivity act;

        public GradesAdapter(FragmentManager fm, GradesViewerActivity act){
            super(fm);
            this.act = act;
        }

        @Override
        public Fragment getItem(int position) {
            GradesListFragment frag = new GradesListFragment();
            frag.setActivity(act);
            frag.setSemester(position + 1);
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class GradesListFragment extends ListFragment {
        private int semester;
        private GradesViewerActivity act;

        public GradesListFragment(){
            super();
        }

        public void setSemester(int semester){
            this.semester = semester;
        }

        public void setActivity(GradesViewerActivity act){
            this.act = act;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            if(act == null){
            }
            else {
                AvgGradesListAdapter adapter = act.new AvgGradesListAdapter(semester);
                setListAdapter(adapter);
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    private class AvgGradesListAdapter extends BaseAdapter{

        private int semester;
        public AvgGradesListAdapter(int semester){
            super();
            this.semester = semester;
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.avg_grade_row, null); //Might need to substitute parent later
            }
            ((TextView)convertView.findViewById(R.id.className)).setText(gradesData.getString("p" + (position + 1), ""));
            if(semester == 1){
                ((TextView) convertView.findViewById(R.id.semesterAverage)).setText(gradesData.getString("p" + (position + 1) + "gradeBookAverageS1", ""));
            }
            else {
                ((TextView) convertView.findViewById(R.id.semesterAverage)).setText(gradesData.getString("p" + (position + 1) + "gradeBookAverageS2", ""));
            }
            return convertView;
        }
    }
}


