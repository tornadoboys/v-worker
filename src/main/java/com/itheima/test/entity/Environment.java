package com.itheima.test.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Environment {
    @ApiModelProperty(position = 1)
    public String py_environment ;
    @ApiModelProperty(position = 2)
    public String py_address ;
    @ApiModelProperty(position = 3)
    public List<String> input_paths;
    @ApiModelProperty(position = 4)
    public List<String>  out_paths;
    @ApiModelProperty(position = 5)
    public List<String> test_paths;
    @ApiModelProperty(position = 6)
    public List<String> shp_paths;
    @ApiModelProperty(position = 7)
    public List<String> etc1_paths;
    @ApiModelProperty(position = 8)
    public List<String> etc2_paths;

}
