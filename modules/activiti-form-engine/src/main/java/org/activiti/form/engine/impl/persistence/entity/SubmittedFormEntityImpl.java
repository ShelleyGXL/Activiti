/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.form.engine.impl.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.form.engine.FormEngineConfiguration;
import org.activiti.form.engine.impl.context.Context;

/**
 * @author Joram Barrez
 * @author Tijs Rademakers
 */
public class SubmittedFormEntityImpl implements SubmittedFormEntity, Serializable {

  private static final long serialVersionUID = 1L;

  protected String id;
  protected String formId;
  protected String taskId;
  protected String processInstanceId;
  protected String processDefinitionId;
  protected Date submittedDate;
  protected String submittedBy;
  protected String formValuesId;
  protected ResourceEntity resourceEntityRef;
  protected String tenantId = FormEngineConfiguration.NO_TENANT_ID;
  
  public Object getPersistentState() {
    Map<String, Object> persistentState = new HashMap<String, Object>();
    persistentState.put("formValuesId", this.formValuesId);
    return persistentState;
  }

  // getters and setters
  // //////////////////////////////////////////////////////

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public Date getSubmittedDate() {
    return submittedDate;
  }

  public void setSubmittedDate(Date submittedDate) {
    this.submittedDate = submittedDate;
  }

  public String getSubmittedBy() {
    return submittedBy;
  }

  public void setSubmittedBy(String submittedBy) {
    this.submittedBy = submittedBy;
  }

  public String getFormValuesId() {
    return formValuesId;
  }

  public void setFormValuesId(String formValuesId) {
    this.formValuesId = formValuesId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
  
  public byte[] getFormValueBytes() {
    ensureResourceRefInitialized();
    return resourceEntityRef.getBytes();
  }

  public void setFormValueBytes(byte[] bytes) {
    ensureResourceRefInitialized();
    if (resourceEntityRef.getId() == null) {
      if (bytes != null) {
        ResourceEntityManager resourceEntityManager = Context.getCommandContext().getResourceEntityManager();
        resourceEntityRef.setName("form-" + formId);
        resourceEntityRef.setBytes(bytes);
        resourceEntityManager.insert(resourceEntityRef);
        formValuesId = resourceEntityRef.getId();
      }
    } else {
      resourceEntityRef.setBytes(bytes);
    }
  }

  public ResourceEntity getFormValueResourceEntityRef() {
    return resourceEntityRef;
  }
  
  protected void ensureResourceRefInitialized() {
    if (resourceEntityRef == null) {
      resourceEntityRef = Context.getCommandContext().getResourceEntityManager().create();
    }
  }
  
  public String toString() {
    return "SubmittedFormEntity[" + id + "]";
  }

}
