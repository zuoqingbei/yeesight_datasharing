import requests,json
import smtplib
from email.mime.text import MIMEText

from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator

from datetime import datetime, timedelta
import pendulum
local_tz = pendulum.timezone("Asia/Shanghai")

dag_name = "{{dagName}}"
dag_schedule_interval = '{{schedule}}'
default_args = {
    'owner': '1169-airflow',
    'depends_on_past': False,
    'start_date': datetime({{startDate}},tzinfo=local_tz),
    'email': ['dongshihai@haier.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=3)
}

dag = DAG(dag_name, default_args=default_args,schedule_interval=dag_schedule_interval)



def print_context(ds, **kwargs):
    return '%s start to exec!'%(dag_name)
task_start = PythonOperator(
    task_id=dag_name+'_startlog',
    python_callable = print_context,
    provide_context =True,
    dag=dag
)
task_email_exec = PythonOperator(
    task_id=dag_name+'_startlog',
    python_callable = send_email,
    provide_context =True,
    dag=dag
)
def send_email(ds, **kwargs):
	r = requests.get(${url})
	json2python = json.loads(r.text)
	sl=0
	for key, value in json2python.items():
		if key=='mail':
			print('ok')
		elif 'sql' in key:
			print('ok')
		elif 'yingshe' in key:
			sl=sl+1
	table=''
	s=[]
	for i in range(0,sl):
		table='<table border="1">'
		j=i+1
		#处理列表
		sql=json2python.get('sql'+str(j))
		yingshe=json2python.get('yingshe'+str(j))
		table=table+' '+'<tr>'
		for value in yingshe:
			table=table+' '+'<th>'+value.get('chinese_name')+'</th>'
		table=table+' '+'</tr>'
		for value in sql:
			table=table+' '+'<tr>'
			for value1 in yingshe:
				table=table+' '+'<td>'+value.get(value1.get('english_name'))+'</td>'
			table=table+' '+'</tr>'
		table=table+' '+'</table>'
		s.append(table)
	#print("ok1:",s)
	strTb=""	
	for tb in s:
		if strTb=="":
			strTb=tb
		else:
			strTb=strTb+"<p/>"+tb
	print(strTb)
	#发送邮件
	msg_from='${msgFrom}'
	passwd='${passwd}'
	msg_to='${msgTo}'                        
	subject="${subject}"
	content='<style type="text/css"> \
	table { \
	font-family: verdana,arial,sans-serif; \
	font-size:11px; \
	color:#333333; \
	border-width: 1px; \
	border-color: #666666; \
	border-collapse: collapse; \
	} \
	table th { \
	border-width: 1px; \
	padding: 8px; \
	border-style: solid; \
	border-color: #666666; \
	background-color: #dedede; \
	} \
	table td { \
	border-width: 1px; \
	padding: 8px; \
	border-style: solid; \
	border-color: #666666; \
	background-color: #ffffff; \
	} \
	</style> ' \
	+ strTb
	msg = MIMEText(content,_subtype='html',_charset='utf-8')
	msg['Subject'] = subject
	msg['From'] = msg_from
	msg['To'] = msg_to

	s = smtplib.SMTP_SSL("${smtp}",465)
	s.login(msg_from, passwd)
	s.sendmail(msg_from, msg_to, msg.as_string())
	print ("发送成功")
    return '%s end to send!'%(dag_name)
	
task_email_exec.set_upstream(task_start)