/*
 * ZomB Dashboard System <http://firstforge.wpi.edu/sf/projects/zombdashboard>
 * Copyright (C) 2011, Patrick Plenefisch and FIRST Robotics Team 451 "The Cat Attack"
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This file can be redistributed under the Lesser GNU General Public License found in REDIST LICENSE.txt
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.thecatattack.system451.communication.dashboard;

public final class ZomBModes
{

    int V;
    public static final ZomBModes DBPacket = new ZomBModes(0x01);
    public static final ZomBModes TCP = new ZomBModes(0x02);
    public static final ZomBModes Both = new ZomBModes(0x03);
    public static final ZomBModes RemoteData = new ZomBModes(0x10);
    public static final ZomBModes AllTCP = new ZomBModes(0x12);
    public static final ZomBModes All = new ZomBModes(0xFF);
    public static final ZomBModes DBPacketAndRemoteData = new ZomBModes(0x11);

    private ZomBModes(int v)
    {
        V = v;
    }
}
