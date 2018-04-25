package discord.gdd.tab.tablist;

import com.google.common.base.Preconditions;

import discord.gdd.tab.Tab;
import discord.gdd.tab.item.BlankTabItem;
import discord.gdd.tab.item.TabItem;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * Uma implementa��o do SimpleTabList que se comporta como uma tabela HTML / CSS.
 * Tem colunas e linhas onde (0,0) � o canto superior esquerdo e (columns - 1, rows - 1)
 * � o canto superior direito.
 *
 * Ele suporta algumas opera��es extravagantes, como preencher uma parte da mesa
 * em qualquer dire��o.
 */
public class TableTabList extends SimpleTabList {
    private final int columns;
    private final int rows;
    private final TableBox box;

    public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
	

	public TableTabList(Tab tabbed, Player player, int columns, int minColumnWidth, int maxColumnWidth) {
        super(tabbed, player, -1, minColumnWidth, maxColumnWidth);
        this.columns = columns;
        this.rows = getMinRows(columns);
        this.box = new TableBox(new TableCell(0, 0), new TableCell(this.columns - 1, this.rows - 1));
    }

    @Override
    public int getMaxItems() {
        return this.columns * this.rows;
    }

    @Override
    public TabItem get(int index) {
        TabItem item = super.get(index);
        if (item instanceof BlankTabItem)
            return null;
        return item;
    }

    @Override
    public TabItem remove(int index) {
        TabItem prev = get(index);
        set(index, new BlankTabItem());
        return prev;
    }

    @Override
    public TableTabList enable() {
        super.enable();
        reset();
        return this;
    }

    @Override
    public TableTabList disable() {
        super.disable();
        return this;
    }

    /**
     * Verifica se a tabela tem um item que n�o � um BlankTabItem na c�lula fornecida.
     * @param cell
     * @return Verdadeiro se o item n�o for um BlankTabItem.
     */
    public boolean contains(TableCell cell) {
        validateCell(cell);
        return super.contains(getIndex(cell));
    }

    /**
     * Verifica se a tab possui um item que n�o � um BlankTabItem na coluna e linha dada.
     * @param column
     * @param row
     * @return Verdadeiro se o item n�o for um BlankTabItem.
     */
    public boolean contains(int column, int row) {
        return contains(getIndex(column, row));
    }

    /**
     * Obt�m o item na c�lula fornecida.
     * @param cell
     * @return O item ou nulo se estiver vazio (BlankTabItem).
     */
    public TabItem get(TableCell cell) {
        validateCell(cell);
        return get(getIndex(cell));
    }

    /**
     * Obt�m a caixa de toda a lista de tab.
     * @return
     */
    public TableBox getBox() {
        return this.box.clone();
    }

    /**
     * LEMBRETE TERMINAR O RESTO DO COMENTARIOS
     */
    public TabItem set(TableCell cell, TabItem item) {
        return set(cell.getColumn(), cell.getRow(), item);
    }

    public TabItem set(int column, int row, TabItem item) {
        return super.set(getIndex(column, row), item);
    }

    public void setTable(Map<TableCell,TabItem> items) {
        for (Entry<TableCell,TabItem> entry : items.entrySet())
            validateCell(entry.getKey());

        Map<Integer,TabItem> indexItems = new HashMap<>(items.size());
        for (Entry<TableCell,TabItem> entry : items.entrySet())
            indexItems.put(getIndex(entry.getKey()), entry.getValue());

        super.set(indexItems);
    }

    public void remove(int column, int row) {
        remove(getIndex(column, row));
    }

    public void remove(TableCell cell) {
        remove(cell.getColumn(), cell.getRow());
    }

    public boolean fill(TableBox box, List<TabItem> items) {
        return fill(box, items, TableCorner.TOPO_ESQUERDA);
    }

    public boolean fill(TableBox box, List<TabItem> items, TableCorner corner) {
        return fill(box, items, corner, FillDirection.HORIZONTAL);
    }

    public boolean fill(TableBox box, List<TabItem> items, TableCorner startCorner, FillDirection direction) {
        return fill(box.getTopoEsquerdo().getColumn(), box.getTopoEsquerdo().getRow(), box.getInferiorDireito().getColumn(), box.getInferiorDireito().getRow(), items, startCorner, direction);
    }

    public boolean fill(int col1, int row1, int col2, int row2, List<TabItem> items, TableCorner startCorner, FillDirection direction) {
        validateCell(col1, row1);
        validateCell(col2, row2);
        Preconditions.checkNotNull(items, "itens n�o podem ser nulos");
        Preconditions.checkNotNull(startCorner, "startCorner n�o podem ser nulos");
        Preconditions.checkNotNull(direction, "direction n�o podem ser nulos");

        Map<Integer,TabItem> map = new HashMap<>();
        Iterator<TabItem> iterator = items.iterator();

        boolean reverseCol = false;
        boolean reverseRow = false;

        if (startCorner == TableCorner.TOPO_DIREITO || startCorner == TableCorner.INFERIOR_DIREITO)
            reverseCol = true;
        if (startCorner == TableCorner.INFERIOR_ESQUERDA || startCorner == TableCorner.INFERIOR_DIREITO)
            reverseRow = true;

        if (direction == FillDirection.HORIZONTAL) {
            for (int row = row1; row <= row2; row++) {
                for (int col = col1; col <= col2; col++) {
                    int fixedCol = reverseCol ? col2 - (col - col1) : col;
                    int fixedRow = reverseRow ? row2 - (row - row1) : row;

                    if (iterator.hasNext())
                        map.put(getIndex(fixedCol, fixedRow), iterator.next());
                }
            }
        }
        else if (direction == FillDirection.VERTICAL) {
            for (int col = col1; col <= col2; col++) {
                for (int row = row1; row <= row2; row++) {
                    int fixedRow = reverseRow ? row2 - (row - row1) : row;
                    int fixedCol = reverseCol ? col2 - (col - col1) : col;

                    if (iterator.hasNext())
                        map.put(getIndex(fixedCol, fixedRow), iterator.next());
                }
            }
        }

        Tab.log(Level.INFO, "Filling " + col1 + "," + row1 + "->" + col2 + "," + row2 + " with " + map.size() + " items");
        set(map);
        return !iterator.hasNext();
    }

    private void reset() {
        Map<Integer,TabItem> newItems = new HashMap<>();
        for (int row = 0; row < this.columns; row++) {
            for (int column = 0; column < this.rows; column++) {
                TabItem item = new BlankTabItem();
                newItems.put(getIndex(row, column), item);
            }
        }
        set(newItems);
    }

    private int getIndex(TableCell cell) {
        return getIndex(cell.getColumn(), cell.getRow());
    }

    private int getIndex(int column, int row) {
        return row + this.rows * column;
    }

    private void validateCell(TableCell cell) {
        validateCell(cell.getColumn(), cell.getRow());
    }

    private void validateCell(int column, int row) {
        Preconditions.checkArgument(row >= 0 && row < this.rows, "row fora do range");
        Preconditions.checkArgument(column >= 0 && column < this.columns, "column fora do range");
    }

    private static int getMinRows(int columns) {
        if (columns == 1)
            return 1;
        else if (columns == 2)
            return 11;
        else if (columns == 3)
            return 14;
        else if (columns == 4)
            return 20;
        else
            throw new RuntimeException("contagem de coluna inv�lida " + columns);
    }

    /**
     * Representa uma c�lula no tab.
     */

    public static class TableCell {
        public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		private int column;
        private int row;
        
        public TableCell (int columns, int rows) {
            this.column = columns;
            this.row = rows;
        }

        public TableCell add(int columns, int rows) {
            this.column += columns;
            this.row += rows;
            return this;
        }

        public TableCell clone() {
            return new TableCell(this.column, this.row);
        }

        @Override
        public String toString() {
            return column + "," + row;
        }
    }

    /**
     * Representa uma �rea da Tab.
     */
    public static class TableBox {
        private final List<TableCell> cells;

        public List<TableCell> getCells() {
			return cells;
		}

		public TableBox(TableCell topLeft, TableCell bottomRight) {
            int width = bottomRight.getColumn() - topLeft.getColumn();

            Preconditions.checkArgument(topLeft.getColumn() <= bottomRight.getColumn(), "col1 deve ser menor ou igual a col2");
            Preconditions.checkArgument(topLeft.getRow() <= bottomRight.getRow(), "row1 deve ser menor ou igual a row2");

            this.cells = new ArrayList<>(4);
            this.cells.add(topLeft.clone());
            this.cells.add(topLeft.clone().add(width, 0));
            this.cells.add(bottomRight.clone());
            this.cells.add(bottomRight.clone().add(-width, 0));
        }

        /**
         * Pegar um canto dessa caixa
         * @param corner
         * @return
         */
        public TableCell get(TableCorner corner) {
            return this.cells.get(corner.ordinal());
        }

        /**
         * Canto superior esquerdo.
         * @return
         */
        public TableCell getTopoEsquerdo() {
            return get(TableCorner.TOPO_ESQUERDA);
        }

        /**
         * Canto superior direito.
         * @return
         */
        public TableCell getTopoDireito() {
            return get(TableCorner.TOPO_DIREITO);
        }

        /**
         * Canto inferior direito.
         * @return
         */
        public TableCell getInferiorDireito() {
            return get(TableCorner.INFERIOR_DIREITO);
        }

        /**
         * Inferior esquerdo.
         * @return
         */
        public TableCell getInferiorEsquerdo() {
            return get(TableCorner.INFERIOR_ESQUERDA);
        }

        /**
         * Get the largura of this box.
         * @return
         */
        public int getWidth() {
            return getTopoDireito().getColumn() - getTopoEsquerdo().getColumn();
        }

        /**
         * Pegar a altura desta caixa.
         * @return
         */
        public int getHeight() {
            return getTopoEsquerdo().getRow() - getInferiorDireito().getRow();
        }

        /**
         * Pegar o tamanho da box.
         * @return
         */
        public int getSize() {
            return getWidth() * getHeight();
        }

        public TableBox clone() {
            return new TableBox(this.getTopoEsquerdo().clone(), this.getInferiorDireito().clone());
        }
    }

    /**
     * Representa um canto do tab.
     */
    public enum TableCorner {
    	TOPO_ESQUERDA,
    	TOPO_DIREITO,
    	INFERIOR_ESQUERDA,
    	INFERIOR_DIREITO
    }

    /**
     * Representa uma dire��o para preencher uma �rea.
     */
    public enum FillDirection {
        HORIZONTAL,
        VERTICAL,
    }
}
