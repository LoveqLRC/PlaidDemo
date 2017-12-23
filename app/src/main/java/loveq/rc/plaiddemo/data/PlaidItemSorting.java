package loveq.rc.plaiddemo.data;

import java.util.Comparator;
import java.util.List;

/**
 * Created by rc on 2017/12/23.
 * Description:
 */

public class PlaidItemSorting {

    public static class PlaidItemComparator implements Comparator<PlaidItem> {

        @Override
        public int compare(PlaidItem lhs, PlaidItem rhs) {
            return Float.compare(lhs.weight, rhs.weight);
        }
    }

    /**
     *  Interface for weighing a group of {@link PlaidItem}s
     */
    public interface PlaidItemGroupWeigher<T extends PlaidItem> {
        void weigh(List<T> items);
    }

    /**
     *  Applies a weight to a group of {@link PlaidItem}s according to their natural order.
     */
    public static class NaturalOrderWeigher implements PlaidItemGroupWeigher<PlaidItem> {

        @Override
        public void weigh(List<PlaidItem> items) {
            final float step = 1f / (float) items.size();
            for (int i = 0; i < items.size(); i++) {
                PlaidItem item = items.get(i);
                item.weight = item.page + ((float) i) * step;
            }
        }
    }
}
