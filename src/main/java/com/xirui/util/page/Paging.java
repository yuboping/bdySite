/**
 * 
 */
package com.xirui.util.page;

import java.io.Serializable;

import com.xirui.util.config.Contants;

/**
 * <p>Title:Paging</p>
 * <p>Description:分页</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年7月18日上午10:29:30
 */
public class Paging implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected int pageSize = Contants.TEN;// 每页记录数，默认10条
	
    protected int iDisplayStart; // 当前页的索引,相当于offset
    
    protected int page = Contants.ONE;// 当前页

	public int getPageSize() { 
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getPage() {
		 //根据当前页开始记录数反推当前为第几页
        if(this.iDisplayStart < 1 || this.pageSize < 1){ //如果开始记录小1，则当前页直接设置为第一页
            this.page = 1;
        }
        else{
            this.page = (this.iDisplayStart  / this.pageSize + 1);
        }
        
        return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
    
    
	
	
}
