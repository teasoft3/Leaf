package com.sankuai.inf.leaf.segment.model;

public class LeafAlloc {
	private String bizTag;
    private Long maxId;
    private Integer step;
    private String updateTime;

    public String getBizTag() {
		return bizTag;
	}

	public void setBizTag(String bizTag) {
		this.bizTag = bizTag;
	}

    public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
