package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    // global value of minimum result.
    long globalResult = Integer.MAX_VALUE;

    // update global minimum
    public synchronized void updateGlobalResult(long result) {
        if (result < this.globalResult) {
            this.globalResult = result;
        }
    }


    @Override
    public long minimumPairwiseDistance(int[] values) {
        if (values == null) {
            return globalResult;
        }

        // creating 4 threads
        Thread LowerLeftThread = new Thread(new LowerLeft(values));
        Thread LowerRightThread = new Thread(new LowerRight(values));
        Thread MiddleThread = new Thread(new Middle(values));
        Thread TopRightThread = new Thread(new TopRight(values));

        // Starting the threads
        LowerLeftThread.start();
        LowerRightThread.start();
        MiddleThread.start();
        TopRightThread.start();

        // Threads wait for each other to finish
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

    // lowerleft thread process
    public class LowerLeft implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;

        public LowerLeft(int[] values) {
            this.value = values;
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

    // lowerright thread process
    public class LowerRight implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;

        public LowerRight(int[] values) {
            this.value = values;
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

    // middle thread process
    public class Middle implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;

        public Middle(int[] values) {
            this.value = values;
        }
        public void run() {
            for (int j = 0; j  + (value.length / 2) < value.length; ++j) {
                for (int i = (value.length) / 2; (i <= j + (value.length / 2)); ++i) {
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

    // topright thread process
    public class TopRight implements Runnable {
        long result = Integer.MAX_VALUE;
        int[] value;

        public TopRight(int[] values) {
            this.value = values;
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
