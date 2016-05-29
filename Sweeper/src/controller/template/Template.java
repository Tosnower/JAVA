package controller.template;

import controller.minefield.Minefield;

public class Template {
	private int[][] template=new int[Minefield.getRow()][Minefield.getCol()];
	public Template(){
		int k=Minefield.getMinenum();
		do{
			int x=(int)(Math.random()*Minefield.getRow());
			int y=(int)(Math.random()*Minefield.getCol());
			if(template[x][y]!=9){
				template[x][y]=9;
				k--;
			}
		}while(k>0);
		for(int i=0;i<Minefield.getRow();i++){
			for(int j=0;j<Minefield.getCol();j++){
				if(template[i][j]!=9){
					int num=0;
					for(int x=-1;x<2;x++){
						for(int y=-1;y<2;y++){
							if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol()&&template[i+x][j+y]==9)
								num++;
						}
					}
					template[i][j]=num;
				}
			}
		}
	}
	public int[][]getTemplate(){
		return this.template;
	}
}
