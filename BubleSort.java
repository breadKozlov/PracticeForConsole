package Chernovik;

public class BubleSort {

    static void bubbleSort(int[] arr) {
            // на каждым шаге справа будет появляться отсортированный элемент
        for (int i = 0; i < arr.length - 1; i++) {
                // оптимизация алгоритма если элементы уже упорядочены
            boolean noSwaps = true;
                // перемещение самого большого элемента вправо
            for (int j = 0; j < arr.length - i - 1; j++) {
                    //если два соседних элемента стоят в неправильом порядке - происходит их перестановка
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    // меняем значение если была хотя бы 1 перестановка
                    noSwaps = false;
                }
            }
            // если не было перестановок - выходим из цикла
            if (noSwaps) {
                break;
            }
        }
    }
}

