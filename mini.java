import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class mini {
    public static String calOutputName ="California";

    // Mapper Class
    public static class WhetherForcastMapper extends Mapper<Object, Text, Text, Text> {
// apple apple ball

        public void map(Object keyOffset, Text dayReport, Context con) throws IOException, InterruptedException
        {
            StringTokenizer strTokens = new StringTokenizer(dayReport.toString(), "\t");
            int counter = 0;
            Float currnetTemp = null;
            Float minTemp = Float.MAX_VALUE;
            Float maxTemp = Float.MIN_VALUE;
            String date = null;
            String currentTime = null;
            String minTempANDTime = null;
            String maxTempANDTime = null;

            while (strTokens.hasMoreElements()) {
                if (counter == 0) {
                    date = strTokens.nextToken();
                }
                else {
                    if (counter % 2 == 1)
                    {
                        currentTime = strTokens.nextToken();
                    }
                    else
                    {  //ct = 10 min = 10
                        currnetTemp =Float.parseFloat(strTokens.nextToken());
                        if (minTemp > currnetTemp) {
                            minTemp = currnetTemp;
                            minTempANDTime = minTemp + "AND" + currentTime;
                        }

                    }
                }
                counter++;
            }
            //(date, (temp,time))
// Write to context - MinTemp, MaxTemp and corresponding time
            Text temp = new Text();
            temp.set(minTempANDTime);
            Text dateText = new Text();
            dateText.set(date);
            con.write(dateText, temp);


        }
    }
    // (29-04-2014,15.7AND12:00:04)
    // 15.7  , 12:00:04
    // date , (min temp : 15.7 min time : 12:00:04)
    // Reducer class
    public static class WhetherForcastReducer extends
            Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
        {
            int counter = 0;
            String reducerInputStr[] = null;
            String f1Time = "";
            String f2Time = "";
            String f1 = "", f2 = "";
            Text result = new Text();
            for (Text value : values) {
                if (counter == 0) {
                    reducerInputStr = value.toString().split("AND");
                    f1 = reducerInputStr[0];
                    f1Time = reducerInputStr[1];

                }

                counter = counter + 1;
            }


                result = new Text("Time: " + f2Time + "\t" + "MinTemp: " + f2 + "\t" + "Time: " + f1Time + " MaxTemp: " + f1);

            context.write(key, result);
        }
    }
    public static void main(String[] args) throws IOException,ClassNotFoundException,InterruptedException
    {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf,"Wheather Statistics of USA");
        job.setMapperClass(WhetherForcastMapper.class);
        job.setReducerClass(WhetherForcastReducer.class) ;
        job.setJarByClass(mini.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);


        // TODO Auto-generated catch block

    }
}


