
from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.python_operator import PythonOperator

from datetime import datetime, timedelta
import pendulum
local_tz = pendulum.timezone("Asia/Shanghai")

dag_name = "{{dagName}}"
dag_schedule_interval = '{{schedule}}'
kettle_command = '''
    {{bashCommand}}
'''
default_args = {
    'owner': '1169-airflow',
    'depends_on_past': False,
    'start_date': datetime({{startDate}},tzinfo=local_tz),
    'email': ['dongshihai@haier.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
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

task_kettle_exec = BashOperator(
    task_id=dag_name+'_kettle_exec',
    bash_command=kettle_command,
    dag=dag)

task_kettle_exec.set_upstream(task_start)