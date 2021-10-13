#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jun  4 18:32:47 2021

@author: taniatoyo
"""

import pandas as pd
df=pd.read_csv("glassdoor_jobs.csv")

#salary_parsing


df=df[df['Salary Estimate']!=-1]
df.shape


salary=df['Salary Estimate'].apply(lambda x: x.split('(')[0])
minus_kd =salary.apply(lambda x: x.replace('K','').replace('$',''))


df['hourly']=df['Salary Estimate'].apply(lambda x: 1 if 'per hour' in x.lower() else 0)

minus_hr=minus_kd.apply(lambda x: x.lower().replace('per hour',''))

df['min_salary']=minus_hr.apply(lambda x: int(x.split('-')[0]))

df['max_salary']=minus_hr.apply(lambda x: int(x.split('-')[1]))

df['avg_salary']=(df['min_salary']+df['max_salary'])/2


#company name only text
df['company_txt']=df.apply(lambda x: x['Company Name'] if x['Rating']<0 else x['Company Name'][:-3],axis=1)

#state field
df['job_state']=df['Location'].apply(lambda x: x.split(',')[1] if ',' in x else x)
df['job_state'].value_counts()
df.columns
df['same_state']=df.apply(lambda x: 1 if x['Location']==x['Headquarters'] else 0, axis=1)

#age of company
df['age']=df['Founded'].apply(lambda x: x if x<0 else 2021-x )

#parsing of job information(python,etc..)
df['Job Description']

#python
df['python']=df['Job Description'].apply(lambda x: 1 if 'python' in x.lower() else 0)
df['python'].value_counts()
#r studio
df['r_studio']=df['Job Description'].apply(lambda x: 1 if 'r studio' in x.lower() else 0)
df['r_studio'].value_counts()

#spark
df['spark']=df['Job Description'].apply(lambda x: 1 if 'spark' in x.lower() else 0)
df['spark'].value_counts()
#excel
df['excel']=df['Job Description'].apply(lambda x: 1 if 'excel' in x.lower() else 0)
df['excel'].value_counts()
#aws
df['aws']=df['Job Description'].apply(lambda x: 1 if 'aws' in x.lower() else 0)
df['aws'].value_counts()

df.to_csv('salary_data_cleaned.csv',index=False)
df=pd.read_csv('salary_data_cleaned.csv')




