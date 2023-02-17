package tictactoe.model;

import gamecore.datastructures.vectors.Vector2i;
import gamecore.observe.IObserver;

public class TicTacToeBoard implements ITicTacToeBoard {

	@Override
	public PieceType Get(Vector2i index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieceType Set(PieceType t, Vector2i index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Remove(Vector2i index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsCellOccupied(Vector2i index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsCellEmpty(Vector2i index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<PieceType> Items() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Vector2i> IndexSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Vector2i> IndexSet(boolean nonempty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<PieceType> Neighbors(Vector2i index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Vector2i> NeighborIndexSet(Vector2i index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Vector2i> NeighborIndexSet(Vector2i index, boolean nonempty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ContainsIndex(Vector2i index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Clear() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int Count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Subscribe(IObserver<TicTacToeEvent> eye) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Unsubscribe(IObserver<TicTacToeEvent> eye) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITicTacToeBoard Clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Vector2i> WinningSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Vector2i> WinningSet(Vector2i use_me) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player Victor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int WinningLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
