package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    long globalResult = Integer.MAX_VALUE;

    public synchronized void updateGlobalResult(long result) {
        if (result < this.globalResult) {
            this.globalResult = result;
        }
    }


    @Override
    public long minimumPairwiseDistance(int[] values) {


        Thread LowerLeftThread = new Thread(new LowerLeft(values, globalResult));
        Thread LowerRightThread = new Thread(new LowerRight(values, globalResult));
        Thread MiddleThread = new Thread(new Middle(values, globalResult));
        Thread TopRightThread = new Thread(new TopRight(values, globalResult));

        LowerLeftThread.start();
        LowerRightThread.start();
        MiddleThread.start();
        TopRightThread.start();

        try {
            LowerLeftThread.join();
            LowerRightThread.join();
            MiddleThread.join();
            TopRightThread.join();
        } catch (Exception e){
            System.out.println("There was an interrupted exception");
        }
        return globalResult;

    }


    public class LowerLeft implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;
        //long globalResult;

        public LowerLeft(int[] values, long globalResult) {
            this.value = values;
            //this.globalResult = globalResult;
        }
        public void run() {
            for (int i = 0; i < (value.length / 2); ++i) {
                for (int j = 0; j < i; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(value[i] - value[j]);
                    if (diff < result) {
                        result = diff;
                    }
                }
            }
            updateGlobalResult(result);
        }
    }

    public class LowerRight implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;
        //long globalResult;

        public LowerRight(int[] values, long globalResult) {
            this.value = values;
            //this.globalResult = globalResult;
        }
        public void run() {
            for (int i = (value.length / 2); i < value.length; ++i) {
                for (int j = 0; j + (value.length / 2) < i; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(value[i] - value[j]);
                    if (diff < result) {
                        result = diff;
                    }
                }
            }
            updateGlobalResult(result);
        }
    }

    public class Middle implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;
        //long globalResult;

        public Middle(int[] values, long globalResult) {
            this.value = value;
            //this.globalResult = globalResult;
        }
        public void run() {
            for (int i = (value.length / 2); i < value.length; ++i) {
                for (int j = 0; j < (value.length / 2); ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(value[i] - value[j]);
                    if (diff < result) {
                        result = diff;
                    }
                }
            }
            updateGlobalResult(result);
        }
    }

    public class TopRight implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;
        //long globalResult;

        public TopRight(int[] values, long globalResult) {
            this.value = values;
            //this.globalResult = globalResult;
        }
        public void run() {
            for (int i = (value.length / 2); i < value.length; ++i) {
                for (int j = (value.length / 2); j < i; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(value[i] - value[j]);
                    if (diff < result) {
                        result = diff;
                    }
                }
            }
            updateGlobalResult(result);
        }
    }
}
