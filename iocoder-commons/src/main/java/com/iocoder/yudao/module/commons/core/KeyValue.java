package com.iocoder.yudao.module.commons.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Key Value 的键值对
 *
 * @author wu kai
 * @date 2022/6/21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValue<K, V> {
    private K key;
    private V value;
}
