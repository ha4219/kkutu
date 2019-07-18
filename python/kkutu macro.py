#!/usr/bin/env python
# coding: utf-8
from selenium import webdriver
import csv
import random

# setting:
URL = 'http://localhost/'
ID = 'GUEST1109'

driver = webdriver.Chrome('C:\Chrome_Driver\chromedriver.exe')
driver.get(URL)

f = open('kkutu_ko.csv', 'r')
reader = csv.reader(f)

result = [line for line in reader]

f.close()

data = dict()
for word in result:
    if word[0][0] not in data.keys():
        data[word[0][0]] = [word[0]]
    else:
        data[word[0][0]].append(word[0])
        
for word_list in data.values():
    data[word_list[0][0]] = sorted(word_list, key=len, reverse=True)


def start():
    driver.find_element_by_xpath('//*[@id="game-start"]').click()


def createRoom():
    driver.find_element_by_xpath('//*[@id="Middle"]/div[24]/div/div/div').click()
    driver.find_element_by_xpath('//*[@id="room-ok"]').click()


def now():
    tmp = driver.find_element_by_xpath('//*[@id="Middle"]/div[27]/div/div[1]/div[5]/div/div[1]')
    if('(' in tmp.text):
        return tmp.text.split('(')
    return tmp.text


def send(msg):
    driver.find_element_by_xpath('//*[@id="Talk"]').send_keys(msg)
    driver.find_element_by_xpath('//*[@id="ChatBtn"]').click()


def get_words(start):
    if start in data.keys():
        return data[start]
    else:
        return ['단어가 없어']


def attack():
    starts = now()
    for start in starts:
        tmp = get_words(start.replace(')', ''))
        my_list = tmp
        if len(my_list) == 0:
            print('단어를 찾을 수 없습니다.')
            return
        send(my_list[0])
        del my_list[0]


def auto():
    while True:
        now_player = driver.find_elements_by_class_name('game-user-current')
        if len(now_player) == 0:
            continue
        target = now_player[0].find_elements_by_class_name('game-user-name')
        if len(target) == 0:
            continue
        player_name = target[0].text
        if len(player_name) > 0 and player_name == ID:
            attack()


data = dict()
for word in result:
    if word[0][0] not in data.keys():
        data[word[0][0]] = [word[0]]
    else:
        data[word[0][0]].append(word[0])
        
for word_list in data.values():
    data[word_list[0][0]] = sorted(word_list, key=len, reverse=True)

if __name__ == "__main__":
    auto()
